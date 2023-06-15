package com.example.capstone_idoeat.ui.scan_result

import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_idoeat.databinding.FragmentScanResultBinding
import com.example.capstone_idoeat.object_detection.Classifier
import com.example.capstone_idoeat.ui.detail.food.DetailFoodActivity

class ScanResultFragment() : Fragment() {
    private var imageUri: Uri? = null
    private var imageResult : Bitmap? = null
    private var results: List<Classifier.Recognition?> = emptyList()
    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var scanResultViewModel: ScanResultViewModel
    private lateinit var foodScanAdapter: FoodScanAdapter

    companion object {
        fun newInstance(uri: Uri?, results: List<Classifier.Recognition?>): ScanResultFragment {
            val fragment = ScanResultFragment()
            fragment.imageUri = uri
            fragment.results = results
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanResultBinding.inflate(inflater, container, false)
        val view = binding.root

        scanResultViewModel = ViewModelProvider(this).get(ScanResultViewModel::class.java)

        // Mendapatkan gambar dan hasil dari argumen
        arguments?.let {
            imageUri = it.getParcelable("imageUri")
            results = it.getSerializable("results") as List<Classifier.Recognition?>
        }

        // Tampilkan gambar di ImageView
        imageResult?.let {
            binding.ivPictureResult.setImageBitmap(it)
        }

        // Tampilkan hasil deteksi pada gambar
        binding.ivPictureResult.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Hapus listener agar tidak dipanggil berulang kali
                binding.ivPictureResult.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Dapatkan ukuran aktual ImageView setelah diukur
                val imageViewWidth = binding.ivPictureResult.width
                val imageViewHeight = binding.ivPictureResult.height

                // Dapatkan ukuran aktual gambar
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
                val imageWidth = bitmap.width
                val imageHeight = bitmap.height

                // Hitung faktor skala untuk mengubah koordinat hasil deteksi ke koordinat ImageView
                val widthScale = imageViewWidth.toFloat() / imageWidth
                val heightScale = imageViewHeight.toFloat() / imageHeight

                // Buat canvas dan paint untuk menggambar kotak
                val overlay = Bitmap.createBitmap(imageViewWidth, imageViewHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(overlay)
                val paint = Paint().apply {
                    color = Color.RED
                    style = Paint.Style.STROKE
                    strokeWidth = 5f
                }

                // Gambar kotak pada hasil deteksi
                for (result in results) {
                    result?.let {
                        val location = it.location
                        val scaledLeft = (location.left * widthScale).toInt()
                        val scaledTop = (location.top * heightScale).toInt()
                        val scaledRight = (location.right * widthScale).toInt()
                        val scaledBottom = (location.bottom * heightScale).toInt()

                        // Gambar kotak pada canvas
                        canvas.drawRect(
                            scaledLeft.toFloat(),
                            scaledTop.toFloat(),
                            scaledRight.toFloat(),
                            scaledBottom.toFloat(),
                            paint
                        )

                        // Gambar teks di atas kotak
                        val tag = "${it.title} - ${(it.confidence!! * 100).toInt()}%"
                        val textPaint = Paint().apply {
                            color = Color.RED
                            textSize = 30f
                        }
                        val textWidth = textPaint.measureText(tag)
                        canvas.drawText(
                            tag,
                            (scaledLeft.toFloat() + (scaledRight.toFloat() - scaledLeft.toFloat()) / 2 - textWidth / 2),
                            (scaledTop.toFloat() - 10),
                            textPaint
                        )
                    }
                }

                // Gabungkan gambar asli dengan overlay yang berisi kotak
                val combinedBitmap = Bitmap.createBitmap(imageViewWidth, imageViewHeight, bitmap.config)
                val combinedCanvas = Canvas(combinedBitmap)
                combinedCanvas.drawBitmap(bitmap, null, RectF(0f, 0f, imageViewWidth.toFloat(), imageViewHeight.toFloat()), null)
                combinedCanvas.drawBitmap(overlay, null, RectF(0f, 0f, imageViewWidth.toFloat(), imageViewHeight.toFloat()), null)

                // Tampilkan gambar hasil dengan kotak pada ImageView
                binding.ivPictureResult.setImageBitmap(combinedBitmap)
            }
        })

        if (results.isNotEmpty()) {
            // Tampilkan hasil di TextView
            val recognitionText = StringBuilder()
            for (result in results) {
                result?.let {
                    val index = results.indexOf(result) + 1
                    val className = it.title
                    val confidence = it.confidence
                    val location = it.location
                    val formattedLocation = "Left: ${location.left}, Top: ${location.top}, Right: ${location.right}, Bottom: ${location.bottom}"
                    val resultString = "$index. $className (${(confidence!! * 100).toInt()}%)"
                    recognitionText.append(resultString).append("\n")
                }
            }
            binding.tvProbability.text = recognitionText.toString()
        } else {
            binding.tvProbability.text = ""
            binding.tvDescription.text = "No result"
        }

        scanResultViewModel = ViewModelProvider(this).get(ScanResultViewModel::class.java)
        foodScanAdapter = FoodScanAdapter(emptyList(), results as List<Classifier.Recognition>,{
            val intent = Intent(requireContext(), DetailFoodActivity::class.java)
            intent.putExtra("productId", it)
            startActivity(intent)
        })

        binding.rvFoodScanResult.layoutManager = LinearLayoutManager(requireContext())

        val searchTitleFood = if (results.isNotEmpty()) {
            if (results[0]?.title?.trim() != "chocolate") results[0]?.title else results[1]?.title
        } else {
            ""
        }
        if (searchTitleFood != null) {
            scanResultViewModel.getFilterdFoodList(searchTitleFood,
                results as List<Classifier.Recognition>
            ).observe(viewLifecycleOwner, Observer { foodList ->
                foodScanAdapter = FoodScanAdapter(foodList, results as List<Classifier.Recognition>,{
                    val intent = Intent(requireContext(), DetailFoodActivity::class.java)
                    intent.putExtra("productId", it)
                    startActivity(intent)
                })
                binding.rvFoodScanResult.adapter = foodScanAdapter
            })
        }
        return view
    }
}