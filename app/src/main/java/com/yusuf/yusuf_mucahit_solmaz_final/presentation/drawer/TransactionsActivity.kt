package com.yusuf.yusuf_mucahit_solmaz_final.presentation.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yusuf.yusuf_mucahit_solmaz_final.R
import com.yusuf.yusuf_mucahit_solmaz_final.core.utils.AppUtils.getAppLocale
import com.yusuf.yusuf_mucahit_solmaz_final.data.datastore.repo.UserSessionRepository
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.ActivityTransactionsBinding
import com.yusuf.yusuf_mucahit_solmaz_final.databinding.NavHeaderMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsActivity: AppCompatActivity() {

    @Inject
    lateinit var session: UserSessionRepository



    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTransactionsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_category,R.id.nav_favorites, R.id.nav_cart,R.id.nav_profile
            ), drawerLayout
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_favorites) {
                binding.appBarMain.toolbar.setBackgroundResource(R.drawable.favorites_appbar_bg)
            }
            else{
                binding.appBarMain.toolbar.setBackgroundResource(R.drawable.appbar_bg)
            }
        }

        val headerView = navView.getHeaderView(0)
        val navHeaderMainBinding = NavHeaderMainBinding.bind(headerView)

        navHeaderMainBinding.currentUserName.text = session.getUser()?.username
        navHeaderMainBinding.currentUserEmail.text = session.getUser()?.email

        Glide.with(this)
            .load(session.getUser()?.image)
            .into(navHeaderMainBinding.imageView)
            .clearOnDetach()
    }


    override fun attachBaseContext(newBase: Context) {
        val config = getAppLocale(newBase)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}