package com.example.capstone_idoeat.ui.recommendation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.example.capstone_idoeat.R
import com.example.capstone_idoeat.databinding.FragmentFilterRecommendationBinding

class FilterRecommendationFragment : Fragment() {
    private var _binding: FragmentFilterRecommendationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterRecommendationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up SeekBar
        val maxPrice = 100000
        binding.seekBarMaxPrice.max = maxPrice

        binding.seekBarMaxPrice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val selectedPrice = progress.toDouble() / maxPrice * maxPrice
                binding.textViewMaxPrice.text = "Max Price: $selectedPrice"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up Spinner
        val calorieOptions = arrayOf("Rendah, <500kkal", "Sedang, 500 - 1200kkal", "Tinggi, >1200kkal")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, calorieOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCalorieType.adapter = adapter
    }

    companion object {
        // kosong
    }
}