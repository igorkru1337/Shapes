package com.example.myapplication

import com.example.myapplication.shape.Cube
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CubeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val view = GLSurfaceView(this)
		view.setRenderer(Cube())
		setContentView(view)
	}
}