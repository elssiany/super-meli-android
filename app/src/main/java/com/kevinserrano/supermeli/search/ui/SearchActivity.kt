package com.kevinserrano.supermeli.search.ui

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.max
import com.kevinserrano.supermeli.databinding.ActivitySearchBinding
import com.kevinserrano.supermeli.utils.getDips
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            initAnimation()
    }

    private fun initViews() {

    }

    private fun initAnimation() {
        val viewTreeObserver = binding.navHostFragment.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    circularRevealActivity()
                    binding.navHostFragment.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    private fun circularRevealActivity() {
        val cx = binding.navHostFragment.right - resources.getDips()
        val cy = binding.navHostFragment.bottom - resources.getDips()
        val finalRadius =
            max(binding.navHostFragment.width, binding.navHostFragment.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            binding.navHostFragment,
            cx,
            cy, 0f,
            finalRadius
        )
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                initViews()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        circularReveal.duration = 400
        binding.navHostFragment.visibility = View.VISIBLE
        circularReveal.start()
    }

    private fun circularRevealOutActivity() {
        val cx = binding.navHostFragment.width - resources.getDips()
        val cy = binding.navHostFragment.bottom - resources.getDips()
        val finalRadius =
            max(binding.navHostFragment.width, binding.navHostFragment.height).toFloat()
        val circularReveal =
            ViewAnimationUtils.createCircularReveal(binding.navHostFragment, cx, cy, finalRadius, 0f)
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                binding.navHostFragment.visibility = View.INVISIBLE
                finish()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        circularReveal.duration = 400
        circularReveal.start()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        circularRevealOutActivity()
    }
}