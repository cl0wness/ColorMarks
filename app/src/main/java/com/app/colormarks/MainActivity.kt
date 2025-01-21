package com.app.colormarks

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.red
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var etMark: EditText

    private lateinit var btnRandom: Button

    private lateinit var tvRandom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etMark = findViewById(R.id.etMark)
        btnRandom = findViewById(R.id.btnRandom)
        tvRandom = findViewById(R.id.tvRandom)

        registerForContextMenu(etMark)
        registerForContextMenu(tvRandom)

        btnRandom.setOnClickListener { setRandom() }
    }

    private fun setRandom() {
        val number = Random.nextInt(1, 51)
        tvRandom.text = number.toString()
    }

    private var currentContextView: View? = null

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        currentContextView = v // так как меню одно, надо сохранять вью, которая нажата в этот раз
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.colorValue -> {
                currentContextView?.id?.let {
                    when (it) {
                        R.id.etMark -> {
                            val colorId = when (etMark.text.toString()) {
                                "1" -> R.color.orange
                                "2" -> R.color.yellow
                                "3" -> R.color.green
                                "4" -> R.color.blue
                                "5" -> R.color.red
                                else -> R.color.transparent
                            }
                            etMark.setBackgroundColor(resources.getColor(colorId, null))
                        }

                        R.id.tvRandom -> {
                            val colorId = when (tvRandom.text.toString().toIntOrNull()) {
                                in 1..10 -> R.color.red
                                in 11..20 -> R.color.orange
                                in 21..30 -> R.color.yellow
                                in 31..40 -> R.color.green
                                in 41..50 -> R.color.blue
                                else -> R.color.transparent
                            }
                            tvRandom.setBackgroundColor(resources.getColor(colorId, null))
                        }

                        else -> {}
                    }
                }
            }

            R.id.exitFromApp -> {
                finish()
            }

            else -> return super.onContextItemSelected(item)
        }
        return true
    }
}