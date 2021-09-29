package com.example.myapplication.shape

import javax.microedition.khronos.opengles.GL10

import android.opengl.GLU

import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import kotlin.math.cos
import kotlin.math.sin

class Sphere(R: Float) : GLSurfaceView.Renderer {

	var mVertexBuffer: FloatBuffer
	private val mTranslucentBackground = false
	var n = 0
	var sz = 0
	private var angle = 0f
	private fun draw(gl: GL10) {
		gl.glFrontFace(GL10.GL_CCW)
		gl.glEnable(GL10.GL_CULL_FACE)
		gl.glCullFace(GL10.GL_BACK)
		gl.glEnable(GL10.GL_BLEND)
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer)
		var red = 0.0f
		val green = 0.25f
		val blue = 0.5f
		var i = 0
		while (i < n) {
			gl.glColor4f(red, green, blue, 1.0f)
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, i, 4)
			if (red >= 1.0f) {
				red = 0.0f
			}
			red += 0.05f
			i += 4
		}
	}

	override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
		gl.glDisable(GL10.GL_DITHER)
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
		if (mTranslucentBackground) {
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
		} else {
			gl.glClearColor(1f, 1f, 1f, 1f)
			gl.glEnable(GL10.GL_CULL_FACE)
			gl.glShadeModel(GL10.GL_SMOOTH)
			gl.glEnable(GL10.GL_DEPTH_TEST)
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
	}

	override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
		gl.glViewport(0, 0, width, height)
		gl.glMatrixMode(GL10.GL_PROJECTION)
		gl.glLoadIdentity()
		val ratio = width.toFloat() / height
		GLU.gluPerspective(gl, 45.0f, ratio, 1f, 100f)
	}

	override fun onDrawFrame(gl: GL10) {
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
		gl.glMatrixMode(GL10.GL_MODELVIEW)
		gl.glLoadIdentity()
		gl.glTranslatef(0f, 0f, -3.0f)
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f)
		draw(gl)
		angle += .4f
	}

	init {
		val dtheta = 15
		val dphi = 15
		var phi: Int
		val dtor = (Math.PI / 180.0f).toFloat()
		var byteBuf: ByteBuffer = ByteBuffer.allocateDirect(5000 * 3 * 4)
		byteBuf.order(ByteOrder.nativeOrder())
		mVertexBuffer = byteBuf.asFloatBuffer()
		byteBuf = ByteBuffer.allocateDirect(5000 * 2 * 4)
		byteBuf.order(ByteOrder.nativeOrder())
		var theta: Int = -90
		while (theta <= 90 - dtheta) {
			phi = 0
			while (phi <= 360 - dphi) {
				sz++
				mVertexBuffer.put((cos((theta * dtor).toDouble()) * cos((phi * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put((cos((theta * dtor).toDouble()) * sin((phi * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put(sin((theta * dtor).toDouble()).toFloat() * R)
				mVertexBuffer.put((cos(((theta + dtheta) * dtor).toDouble()) * cos((phi * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put((cos(((theta + dtheta) * dtor).toDouble()) * sin((phi * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put(sin(((theta + dtheta) * dtor).toDouble()).toFloat() * R)
				mVertexBuffer.put((cos(((theta + dtheta) * dtor).toDouble()) * cos(((phi + dphi) * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put((cos(((theta + dtheta) * dtor).toDouble()) * sin(((phi + dphi) * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put(sin(((theta + dtheta) * dtor).toDouble()).toFloat() * R)
				n += 3
				mVertexBuffer.put((cos((theta * dtor).toDouble()) * cos(((phi + dphi) * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put((cos((theta * dtor).toDouble()) * sin(((phi + dphi) * dtor).toDouble())).toFloat() * R)
				mVertexBuffer.put(sin((theta * dtor).toDouble()).toFloat() * R)
				n++
				phi += dphi
			}
			theta += dtheta
		}
		mVertexBuffer.position(0)
	}
}