package com.girogevoro.dictionary.view.history

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.fragment.app.DialogFragment
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import coil.load
import coil.size.OriginalSize
import com.girogevoro.dictionary.databinding.FragmentTranslationDialogBinding

class TranslationDialogFragment(
    private val header: String,
    private val translation: String,
    private val imageLink: String,
) : DialogFragment() {

    private var _viewBinding: FragmentTranslationDialogBinding? = null
    private val viewBinding: FragmentTranslationDialogBinding
        get() {
            return _viewBinding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentTranslationDialogBinding.inflate(inflater, container, false).also {
            _viewBinding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewBinding) {
            tvHeader.text = header
            tvTranslation.text = translation
            useCoilToLoadPhoto(iv, imageLink)

        }
    }

    private fun useCoilToLoadPhoto(iv: ImageView, imageLink: String) {
        iv.load("https:$imageLink") {
            size(OriginalSize)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ValueAnimator.ofFloat(
                    10f,
                    1f
                ).apply {
                    duration = 3000
                    interpolator = FastOutLinearInInterpolator()
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        val blurEffect = RenderEffect.createBlurEffect(value, value, Shader.TileMode.DECAL)
                        iv.setRenderEffect(blurEffect)
                    }
                }.start()
            }



        }
    }

}