package com.example.asteroid_radar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.asteroidradar.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHosFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHosFragment.navController
        setupActionBarWithNavController(
            navController
        )


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(
            R.id.nav_host_fragment
        )
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}
