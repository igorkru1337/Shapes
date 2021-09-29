package com.example.myapplication.shape

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig

class Square : GLSurfaceView.Renderer {

	private val mFVertexBuffer: FloatBuffer
	private val mColorBuffer: ByteBuffer
	private val mIndexBuffer: ByteBuffer
	private val mTranslucentBackground = false

	private fun draw(gl: GL10) {
		gl.glFrontFace(GL10.GL_CW)
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mFVertexBuffer)
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mColorBuffer)
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_BYTE, mIndexBuffer)
		gl.glFrontFace(GL10.GL_CCW)
	}

	override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
		gl.glDisable(GL10.GL_DITHER)
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
		if (mTranslucentBackground) {
			gl.glClearColor(0f, 0f, 0f, 0f)
		} else {
			gl.glClearColor(1f, 1f, 1f, 1f)
			gl.glEnable(GL10.GL_CULL_FACE)
			gl.glShadeModel(GL10.GL_SMOOTH)
			gl.glEnable(GL10.GL_DEPTH_TEST)
		}
	}

	override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
		gl.glViewport(0, 0, width, height)
		val ratio = width.toFloat() / height
		gl.glMatrixMode(GL10.GL_PROJECTION)
		gl.glLoadIdentity()
		gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)
	}

	override fun onDrawFrame(gl: GL10) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
		gl.glMatrixMode(GL10.GL_MODELVIEW)
		gl.glLoadIdentity()
		gl.glTranslatef(0.0f, 0.0f, -3.0f)
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY)
		draw(gl)
	}

	init {
		val vertices = floatArrayOf(
			-1.0f, -1.0f,
			1.0f, -1.0f,
			-1.0f, 1.0f,
			1.0f, 1.0f
		)
		val colors = byteArrayOf(
			1, 1, 0, 1,
			1, 1, 0, 1,
			1, 1, 0, 1,
			1, 1, 0, 1,
		)
		val indices = byteArrayOf(
			0, 3, 1,
			0, 2, 3
		)
		val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
		vbb.order(ByteOrder.nativeOrder())
		mFVertexBuffer = vbb.asFloatBuffer()
		mFVertexBuffer.put(vertices)
		mFVertexBuffer.position(0)
		mColorBuffer = ByteBuffer.allocateDirect(colors.size * 4)
		mColorBuffer.put(colors)
		mColorBuffer.position(0)
		mIndexBuffer = ByteBuffer.allocateDirect(indices.size)
		mIndexBuffer.put(indices)
		mIndexBuffer.position(0)
	}
}