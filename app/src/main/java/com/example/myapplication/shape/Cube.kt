package com.example.myapplication.shape

import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.tan

class Cube : GLSurfaceView.Renderer {

	private val vertexBuffer: FloatBuffer
	private val colorBuffer: ByteBuffer
	private val byteBufferFirst: ByteBuffer
	private val byteBufferSecond: ByteBuffer
	private var mAngle = 0f

	private fun draw(gl: GL10) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
		gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, colorBuffer)
		gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, byteBufferFirst)
		gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, byteBufferSecond)
	}

	override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
		gl.glDisable(GL10.GL_DITHER)
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
		gl.glClearColor(1f, 0f, 0f, 0f)
		gl.glEnable(GL10.GL_CULL_FACE)
		gl.glShadeModel(GL10.GL_SMOOTH)
		gl.glEnable(GL10.GL_DEPTH_TEST)
	}

	override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
		gl.glViewport(0, 0, width, height)
		val zNear = .1f
		val zFar = 1000f
		gl.glEnable(GL10.GL_NORMALIZE)
		val aspectRatio: Float = width.toFloat() / height.toFloat()
		gl.glMatrixMode(GL10.GL_PROJECTION)
		val size: Float = zNear * tan((1f / 2.0f).toDouble()).toFloat()
		gl.glFrustumf(-size, size, -size / aspectRatio, size / aspectRatio, zNear, zFar)
		gl.glMatrixMode(GL10.GL_MODELVIEW)
	}

	override fun onDrawFrame(gl: GL10) {
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
		gl.glMatrixMode(GL10.GL_MODELVIEW)
		gl.glLoadIdentity()
		gl.glTranslatef(0.0f, 0.0f, -7.0f)
		gl.glRotatef(mAngle, 1.0f, 1.0f, 0.0f)
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY)
		draw(gl)
		mAngle += .4f
	}

	init {
		val vertices = floatArrayOf(
			-1.0f, 1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			1.0f, -1.0f, 1.0f,
			-1.0f, -1.0f, 1.0f,
			-1.0f, 1.0f, -1.0f,
			1.0f, 1.0f, -1.0f,
			1.0f, -1.0f, -1.0f,
			-1.0f, -1.0f, -1.0f
		)
		val colors = byteArrayOf(
			1, 100, 0, 127,
			0, 1, 1, 127,
			0, 0, 0, 127,
			100, 0, 1, 127,
			1, 0, 0, 127,
			0, 100, 0, 127,
			0, 0, 10, 127,
			0, 0, 0, 100
		)
		val triangleFanFirst = byteArrayOf(
			1, 0, 3,
			1, 3, 2,
			1, 2, 6,
			1, 6, 5,
			1, 5, 4,
			1, 4, 0
		)
		val triangleFanSecond = byteArrayOf(
			7, 4, 5,
			7, 5, 6,
			7, 6, 2,
			7, 2, 3,
			7, 3, 0,
			7, 0, 4
		)
		val vbb: ByteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
		vbb.order(ByteOrder.nativeOrder())
		vertexBuffer = vbb.asFloatBuffer()
		vertexBuffer.put(vertices)
		vertexBuffer.position(0)
		colorBuffer = ByteBuffer.allocateDirect(colors.size)
		colorBuffer.put(colors)
		colorBuffer.position(0)
		byteBufferFirst = ByteBuffer.allocateDirect(triangleFanFirst.size)
		byteBufferFirst.put(triangleFanFirst)
		byteBufferFirst.position(0)
		byteBufferSecond = ByteBuffer.allocateDirect(triangleFanSecond.size)
		byteBufferSecond.put(triangleFanSecond)
		byteBufferSecond.position(0)
	}
}