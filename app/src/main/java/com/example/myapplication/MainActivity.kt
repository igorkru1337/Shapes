package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.bottom_navigation)
		val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
		bottomNavigation.setOnNavigationItemSelectedListener { item ->
			when (item.itemId) {
				R.id.square -> {
					val intent = Intent(this@MainActivity, SquareActivity::class.java)
					startActivity(intent)
					true
				}

				R.id.cube   -> {
					val intent = Intent(this@MainActivity, CubeActivity::class.java)
					startActivity(intent)
					true
				}

				R.id.sphere -> {
					val intent = Intent(this@MainActivity, SphereActivity::class.java)
					startActivity(intent)
					true
				}

				else        -> false
			}
		}
	}
}
