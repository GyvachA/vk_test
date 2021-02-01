package ru.gyvacha.vktestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var inputEditText: EditText
    lateinit var showChangedTextView: TextView
    lateinit var changeTextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        inputEditText = findViewById(R.id.inputEditText)
        showChangedTextView = findViewById(R.id.showChangedTextView)
        changeTextButton = findViewById(R.id.changeTextButton)

        changeTextButton.setOnClickListener {
            val inputStr = inputEditText.text.toString().trim()

            if (inputStr.isEmpty()) {
                Toast.makeText(
                        this,
                        getString(R.string.zero_input),
                        Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            showChangedTextView.text = inputStr

            inputEditText.text.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("changed_text", showChangedTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        showChangedTextView.text = savedInstanceState.getString("changed_text")
    }
}