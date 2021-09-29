package com.example.myapplication

import com.example.myapplication.shape.Square
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SquareActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val view = GLSurfaceView(this)
		view.setRenderer(Square())
		setContentView(view)
	}
}