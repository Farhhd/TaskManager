package com.example.myapplicatio.ui.profile

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.example.myapplicatio.R
import com.example.myapplicatio.data.local.Pref
import com.example.myapplicatio.databinding.FragmentHomeBinding
import com.example.myapplicatio.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val pref: Pref by lazy {
        Pref(requireContext())
    }
    private lateinit var bitmap: Bitmap


    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {
            binding.imgProfile.setImageURI(galleryUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    // This property is only valid between onCreateView and
    // onDestroyView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    private fun initListener() {
        binding.imgProfile.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        binding.etName.setText(pref.getName())
        binding.etName.addTextChangedListener {
            pref.saveName(binding.etName.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}