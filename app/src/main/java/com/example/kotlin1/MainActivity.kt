package com.example.kotlin1

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var imageSwitcher: ImageSwitcher
    private lateinit var textSwitcher: TextSwitcher
    private lateinit var btnPrevious: Button
    private lateinit var btnNext: Button
    private lateinit var btnShowDetails: Button
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

        imageSwitcher.setFactory { 
            val imageView = ImageView(applicationContext)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView
        }

        textSwitcher.setFactory {
            val textView = TextView(applicationContext)
            textView.textSize = 20f
            textView.setTextColor(Color.BLACK)
            textView.gravity = Gravity.CENTER
            textView
        }

        btnPrevious.setOnClickListener { showPreviousImage() }
        btnNext.setOnClickListener { showNextImage() }
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
    }

    private fun toggleDetailsVisibility() {
        if (detailsTextView.isVisible) {
            detailsTextView.isVisible = false
            btnShowDetails.text = "Show Details"
        } else {
            val imageView = imageSwitcher.currentView as ImageView
            val drawable: Drawable? = imageView.drawable
            drawable?.let {
                val width = it.intrinsicWidth
                val height = it.intrinsicHeight
                val resolution = "$width x $height"
                detailsTextView.text = "Height: $height, Width: $width, Resolution: $resolution"
            }
            detailsTextView.isVisible = true
            btnShowDetails.text = "Hide Details"
        }
    }
}
