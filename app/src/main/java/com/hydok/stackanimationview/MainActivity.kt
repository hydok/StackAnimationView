package com.hydok.stackanimationview

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<VerticalStackAnimationView>(R.id.verticalStack)
        view.setDuration(500, 500, 500)

        view.launchAnimation {
            it
                .inView(textView("안녕하세요"))
                .inView(textView("점심먹고 나니깐"))
                .inView(textView("너무 졸리네요."))
                .inView(textView("하하하"))
                .clearView()
                .inView(textView("커피..."))
                .inView(textView("한잔..."),30)
                .inView(textView("두잔..."))
        }.doOnEnd {
            Toast.makeText(this, "end...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun textView(text: String): TextView {
        return TextView(this).apply {
            setText(text)
            textSize = 22f
        }
    }
}