package com.example.capstone_idoeat.ui.scan

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.FragmentScanBinding
import com.example.capstone_idoeat.object_detection.Classifier
import com.example.capstone_idoeat.object_detection.TensorFlowImageClassifier
import com.example.capstone_idoeat.ui.scan_result.ScanResultFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private lateinit var classifier: Classifier

    private val MODEL_PATH = "detect_food.tflite"
    private val LABEL_PATH = "labelmap.txt"
    private val INPUT_SIZE = 320

    companion object {
        private const val TAG = "ScanFragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_GALLERY_IMAGE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dashboardViewModel = ViewModelProvider(this).get(ScanViewModel::class.java)
        outputDirectory = getOutputDirectory()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        initTensorFlow()
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnCapture.setOnClickListener { takePhoto() }
        binding.btnSwitchCamera.setOnClickListener { switchCamera() }
        binding.btnGalery.setOnClickListener { openGallery() }
    }

    private fun initTensorFlow() {
        try {
            classifier = TensorFlowImageClassifier.create(
                requireContext().assets,
                MODEL_PATH,
                LABEL_PATH,
                INPUT_SIZE
            )
            startCamera()
        } catch (e: IOException) {
            Log.e(TAG, "Failed to initialize TensorFlow: ${e.message}")
            // Tangani error jika inisialisasi gagal
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (ex: Exception) {
                Log.e(TAG, "Failed to bind camera: $ex")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun switchCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            val newLensFacing = if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }

            val newCameraSelector = CameraSelector.Builder()
                .requireLensFacing(newLensFacing)
                .build()

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    newCameraSelector,
                    preview,
                    imageCapture
                )

                lensFacing = newLensFacing
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to bind camera: $ex")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                    val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                    processImage(savedUri, bitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Error capturing image: ${exception.message}", exception)
                    // Handle capture error
                }
            }
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            selectedImage?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
                processImage(it, bitmap)
            }
        }
    }
    private fun processImage(bitmapUri: Uri?, bitmap: Bitmap?) {
        bitmap?.let {
            if (::classifier.isInitialized) {
                val results = classifier.recognizeImage(it)
                if (results != null) {
                    navigateToResultScan(bitmapUri, results)
                } else {
                    Toast.makeText(requireContext(), "Makanan tidak teridentifikasi", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Classifier not initialized", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun navigateToResultScan(bitmapUri: Uri?, results: List<Classifier.Recognition?>) {
        val resultFragment = ScanResultFragment.newInstance(bitmapUri, results)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_scan, resultFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }
}