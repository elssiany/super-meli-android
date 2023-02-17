package com.kevinserrano.supermeli.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kevinserrano.supermeli.R
import com.kevinserrano.supermeli.databinding.FragmentWelcomeBinding
import com.kevinserrano.supermeli.search.ui.SearchActivity
import com.kevinserrano.supermeli.utils.toHtml

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = getString(R.string.welcome_messages).toHtml()
        binding.animationView.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
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