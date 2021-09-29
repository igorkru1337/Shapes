package com.example.myapplication

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.shape.Sphere

class SphereActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val view = GLSurfaceView(this)
		view.setRenderer(Sphere(0.5f))
		setContentView(view)
	}
}