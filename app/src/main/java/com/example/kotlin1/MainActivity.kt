package com.example.kotlin1

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var imageSwitcher: ImageSwitcher
    private lateinit var textSwitcher: TextSwitcher
    private lateinit var btnPrevious: AppCompatButton
    private lateinit var btnNext: AppCompatButton
    private lateinit var btnShowDetails: AppCompatButton
    private lateinit var detailsTextView: TextView

    private val images = arrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4)
    private val imageInfo = arrayOf(
        "Chevrolet Corvette Z06",
        "Porsche 911 GT3",
        "Lamborghini Huracan EVO",
        "Ferrari F8 Tributo"
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageSwitcher = findViewById(R.id.imageSwitcher)
        textSwitcher = findViewById(R.id.textSwitcher)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnShowDetails = findViewById(R.id.btnShowDetails)
        detailsTextView = findViewById(R.id.detailsTextView)

        val inRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        val outLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
        val inLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        val outRight = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)

        imageSwitcher.setFactory {
            val imageView = ImageView(applicationContext)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView
        }

        textSwitcher.setFactory {
            val textView = object : AppCompatTextView(this) {
                override fun setText(text: CharSequence?, type: BufferType?) {
                    super.setText(text, type)
                    if (!text.isNullOrEmpty()) {
                        val paint = this.paint
                        val width = paint.measureText(text.toString())
                        val textShader: Shader = LinearGradient(0f, 0f, width, this.textSize,
                            intArrayOf(
                                "#8839ef".toColorInt(),
                                "#cba6f7".toColorInt()
                            ),
                            null, Shader.TileMode.CLAMP)
                        this.paint.shader = textShader
                    }
                }
            }
            textView.textSize = 24f
            textView.gravity = Gravity.CENTER
            val typeface = ResourcesCompat.getFont(this, R.font.dancing_script)
            textView.typeface = typeface
            textView
        }

        btnPrevious.setOnClickListener { 
            imageSwitcher.inAnimation = inLeft
            imageSwitcher.outAnimation = outRight
            showPreviousImage() 
        }
        btnNext.setOnClickListener { 
            imageSwitcher.inAnimation = inRight
            imageSwitcher.outAnimation = outLeft
            showNextImage()
        }
        btnShowDetails.setOnClickListener { toggleDetailsVisibility() }

        updateImageAndInfo()
    }

    private fun showPreviousImage() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else images.size - 1
        updateImageAndInfo()
    }

    private fun showNextImage() {
        currentIndex = if (currentIndex < images.size - 1) currentIndex + 1 else 0
        updateImageAndInfo()
    }

    private fun updateImageAndInfo() {
        imageSwitcher.setImageResource(images[currentIndex])
        textSwitcher.setText(imageInfo[currentIndex])
        detailsTextView.isVisible = false
        btnShowDetails.text = "Show Details"
        btnShowDetails.background = ContextCompat.getDrawable(this, R.drawable.gradient_green)
    }

    private fun toggleDetailsVisibility() {
        if (detailsTextView.isVisible) {
            detailsTextView.isVisible = false
            btnShowDetails.text = "Show Details"
            btnShowDetails.background = ContextCompat.getDrawable(this, R.drawable.gradient_green)
        } else {
            val imageView = imageSwitcher.currentView as ImageView
            val drawable: Drawable? = imageView.drawable
            drawable?.let {
                val width = it.intrinsicWidth
                val height = it.intrinsicHeight
                val resolution = "$width x $height"
                detailsTextView.text = "Height: $height, Width: $width\nResolution: $resolution"
                detailsTextView.setTextColor("#9399b2".toColorInt())
            }
            detailsTextView.isVisible = true
            btnShowDetails.text = "Hide Details"
            btnShowDetails.background = ContextCompat.getDrawable(this, R.drawable.gradient_red)
        }
    }
}
