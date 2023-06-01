package com.example.capstone_idoeat.ui.scan_result

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.FragmentScanResultBinding
import com.example.capstone_idoeat.object_detection.Classifier

class ScanResultFragment : Fragment() {
    private var imageUri: Uri? = null
    private var results: List<Classifier.Recognition?> = emptyList()
    private var _binding: FragmentScanResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(imageUri: Uri?, results: List<Classifier.Recognition?>): ScanResultFragment {
            val fragment = ScanResultFragment()
            val args = Bundle()
            args.putParcelable("imageUri", imageUri)
            args.putSerializable("results", ArrayList(results))
            fragment.arguments = args
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

        // Mendapatkan gambar dan hasil dari argumen
        arguments?.let {
            imageUri = it.getParcelable("imageUri")
            results = it.getSerializable("results") as List<Classifier.Recognition?>
        }

        // Tampilkan gambar di ImageView
        imageUri?.let {
            binding.ivPictureResult.setImageURI(it)
        }

        // Tampilkan hasil di TextView
        binding.tvName.text = results.toString()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}