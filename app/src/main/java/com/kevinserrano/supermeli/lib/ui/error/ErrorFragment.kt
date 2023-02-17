package com.kevinserrano.supermeli.lib.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kevinserrano.supermeli.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {

    private var _binding: FragmentErrorBinding? = null

    private val binding get() = _binding!!

    private val messageError:String by lazy {
        arguments?.getString(MESSAGE_ERROR_KEY) ?: ""
    }

    var onRetry: () -> Unit = {}

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSubTitle.text = messageError
        binding.btnRetry.setOnClickListener {
            onRetry()
        }
    }

    override fun onResume() {
        if (!binding.animationView.isAnimating) {
            binding.animationView.resumeAnimation()
        }
        super.onResume()
    }

    override fun onStop() {
        if (binding.animationView.isAnimating) {
            binding.animationView.pauseAnimation()
        }
        super.onStop()
    }

    override fun onDestroyView() {
        if (binding.animationView.isAnimating) {
            binding.animationView.cancelAnimation()
        }
        _binding = null
        super.onDestroyView()
    }
}

const val MESSAGE_ERROR_KEY = "message_error"
