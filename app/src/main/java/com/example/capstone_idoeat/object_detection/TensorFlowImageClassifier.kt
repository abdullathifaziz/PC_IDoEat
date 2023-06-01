package com.example.capstone_idoeat.object_detection

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.graphics.Bitmap
import com.example.capstone_idoeat.object_detection.Classifier.Recognition
import org.tensorflow.lite.Interpreter
import java.io.*
import java.lang.Math.min
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class TensorFlowImageClassifier private constructor(
    private val interpreter: Interpreter,
    private val labelList: List<String>,
    private val inputSize: Int
) : Classifier {

    override fun recognizeImage(bitmap: Bitmap?): List<Recognition?>? {
        val byteBuffer: ByteBuffer = convertBitmapToByteBuffer(bitmap)
        val result = Array(1) { FloatArray(MAX_RESULTS) }
        interpreter.run(byteBuffer, result)
        return getSortedResult(result)
    }

    override fun close() {
        interpreter.close()
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap?): ByteBuffer {
        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(inputSize * inputSize * PIXEL_SIZE * FLOAT32_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(bitmap!!.width * bitmap.height)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val `val` = intValues[pixel++]
                byteBuffer.putFloat(((`val` shr 16 and 0xFF) / 255.0f))
                byteBuffer.putFloat(((`val` shr 8 and 0xFF) / 255.0f))
                byteBuffer.putFloat(((`val` and 0xFF) / 255.0f))
            }
        }
        byteBuffer.rewind()
        return byteBuffer
    }

    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Recognition?> {
        val pq: PriorityQueue<Recognition> = PriorityQueue(
            MAX_RESULTS,
            object : Comparator<Recognition?> {
                override fun compare(p0: Recognition?, p1: Recognition?): Int {
                    return java.lang.Float.compare(p1!!.confidence!!, p0!!.confidence!!)
                }
            })

        val labelSize = labelList.size
        val buffer = ByteBuffer.allocate(FLOAT32_SIZE).order(ByteOrder.nativeOrder())
        for (i in 0 until MAX_RESULTS) {
            buffer.rewind()
            buffer.putFloat(labelProbArray[0][i])
            buffer.rewind()
            val confidence = buffer.float
            if (confidence > THRESHOLD) {
                pq.add(
                    Recognition(
                        "" + i,
                        if (labelSize > i) labelList[i] else "unknown",
                        confidence
                    )
                )
            }
        }

        val recognitions: ArrayList<Recognition?> = ArrayList()
        val recognitionsSize = min(pq.size, MAX_RESULTS)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }
        return recognitions
    }

    companion object {
        private const val MAX_RESULTS = 10
        private const val BATCH_SIZE = 1
        private const val PIXEL_SIZE = 3
        private const val THRESHOLD = 0.1f

        // Konstanta untuk ukuran tipe data FLOAT32
        private const val FLOAT32_SIZE = 4

        @Throws(IOException::class)
        fun create(
            assetManager: AssetManager,
            modelPath: String,
            labelPath: String,
            inputSize: Int
        ): Classifier {
            val model = Interpreter(loadModelFile(assetManager, modelPath))
            val labelList = loadLabelList(assetManager, labelPath)
            return TensorFlowImageClassifier(model, labelList, inputSize)
        }

        @Throws(IOException::class)
        private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
            val fileDescriptor = assetManager.openFd(modelPath)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel: FileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }

        private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
            val labelList: MutableList<String> = ArrayList()

            try {
                val inputStream: InputStream = assetManager.open(labelPath)
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    line?.let { labelList.add(it) }
                }

                reader.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return labelList
        }
    }
}