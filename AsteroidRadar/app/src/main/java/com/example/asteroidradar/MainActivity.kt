package com.example.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.*
import com.example.asteroidradar.R
import com.example.asteroidradar.R.navigation.nav_graph
import com.example.asteroidradar.Worker.AsteroidWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<AsteroidWorker>(
            1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        val navHosFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHosFragment.navController
        setupActionBarWithNavController(
            navController
        )

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidWorker.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
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
