package com.example.dragdrop

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    private lateinit var dragView: ImageView
    private lateinit var bottomLayout: LinearLayoutCompat
    private lateinit var shoppingCard: ImageView
    private lateinit var labelView: MaterialCardView
    private val itemCount by lazy {
        findViewById<TextView>(R.id.itemCount)
    }
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dragView = findViewById(R.id.dragView)
        labelView = findViewById(R.id.labelView)
        shoppingCard = findViewById(
            R.id.shoppingCard
        )
        bottomLayout = findViewById(R.id.bottomLayout)
        bottomLayout.setOnDragListener(dragListener)
        shoppingCard.setOnDragListener(dragListener)
        dragView.setOnLongClickListener {
            val clipText = "This is our Data"
            val item = ClipData.Item(clipText)
            val mineType = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mineType, item)
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            it.visibility = View.VISIBLE
            true
        }

    }

    val dragListener = View.OnDragListener { v, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> true

            DragEvent.ACTION_DRAG_EXITED -> {
                v.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val text = item.text
                //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                v.invalidate()
                when (v) {
                    is ImageView -> {
                        labelView.visibility = View.VISIBLE
                        labelView.alpha = 0f
                        labelView.animate()
                            .alpha(1f)
                            .setDuration(1000)
                            .start()
                        addIntoCard()
                    }
                    is LinearLayoutCompat -> {
                        v.setBackgroundColor(Color.RED)
                    }
                    else -> throw IllegalArgumentException("Undefine View")
                }
//                val destination = v as View
//                val view = event.localState as View
//                val owner = view.parent as ViewGroup
//                if (destination is ImageView) {
//                    destination.setImageResource(R.drawable.android)
//                    bottomLayout.setBackgroundColor(Color.GREEN)
//                    owner.removeView(view)
//                } else if (destination is LinearLayoutCompat) {
//                    destination.setBackgroundColor(Color.parseColor("#000000"))
//                    view.visibility = View.VISIBLE
//                }


                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                v.invalidate()
                true
            }
            else -> false
        }
    }

    private fun addIntoCard() {
        counter++
        itemCount.text = "$counter"
    }
}