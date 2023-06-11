package com.example.capstone_idoeat.object_detection

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Log
import com.example.capstone_idoeat.object_detection.Classifier.Recognition
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.*
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
    //use convertBitmap
        val imageBitmap = convertBitmap(bitmap)

        // Run the inference
        val outputLocations = Array(1) { Array(NUM_DETECTIONS) { FloatArray(4) } }
        val outputClasses = Array(1) { FloatArray(NUM_DETECTIONS) }
        val outputScores = Array(1) { FloatArray(NUM_DETECTIONS) }
        val outputCount = FloatArray(1)

        val outputs = mapOf(
            1 to outputLocations,
            3 to outputClasses,
            0 to outputScores,
            2 to outputCount
        )

        interpreter.runForMultipleInputsOutputs(arrayOf(imageBitmap.buffer), outputs)

        // Retrieve detection results
        val boxes = outputLocations[0] // Bounding box coordinates of detected objects
        val classes = outputClasses[0].map { it.toInt() } // Class index of detected objects
        val scores = outputScores[0] // Confidence of detected objects

        // Create list of recognitions with bounding box coordinates
        val recognitions = ArrayList<Recognition>()
        for (i in 0 until boxes.size) {
            val confidence = scores[i]
            val classIndex = classes[i]
            val className = labelList[classIndex]
            val location = boxes[i]
            val x1 = location[1] * bitmap!!.width
            val y1 = location[0] * bitmap.height
            val x2 = location[3] * bitmap.width
            val y2 = location[2] * bitmap.height
            val recognition = Recognition(
                i.toString(),
                className,
                confidence,
                RectF(x1, y1, x2, y2)
            )
            Log.d("_________TensorFlowImageClassifier", "className: $className" + "---->" + "confidence: $confidence")
            recognitions.add(recognition)
        }

        Log.d("_________OUTPUT", "output: ${Arrays.toString(scores)}")
        return recognitions
    }

    fun convertBitmap(bitmap: Bitmap?): TensorImage {
        // Resize the bitmap to the desired input size (320x320)
        val resizedBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 320, 320, true) }

        // Create a TensorImage object with FLOAT32 data type
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)

        return tensorImage
    }

    override fun close() {
        interpreter.close()
    }

    companion object {
        private const val MAX_RESULTS = 20
        private const val PIXEL_SIZE = 3
        private const val THRESHOLD = 0.1f
        private const val NUM_DETECTIONS = 10
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
            val modelPath = modelPath
            val labelList = loadLabelList(assetManager, labelPath)
            return TensorFlowImageClassifier(model, labelList, inputSize)
        }

        @Throws(IOException::class)
        private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
            val modelFileDescriptor = assetManager.openFd(modelPath)
            val inputStream = FileInputStream(modelFileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = modelFileDescriptor.startOffset
            val declaredLength = modelFileDescriptor.declaredLength
            val modelByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            fileChannel.close()
            inputStream.close()
            return modelByteBuffer
        }

        private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
            val labelList: MutableList<String> = ArrayList()

            try {
                val labelFileInputStream = assetManager.open(labelPath)
                val labelFileReader = BufferedReader(InputStreamReader(labelFileInputStream))
                var line: String?

                while (labelFileReader.readLine().also { line = it } != null) {
                    line?.let { labelList.add(it) }
                }

                labelFileReader.close()
                labelFileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return labelList
        }
    }
}