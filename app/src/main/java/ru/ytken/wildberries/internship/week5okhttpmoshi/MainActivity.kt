package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tagMainFragment = "TAG_MAIN_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, MainFragment(), tagMainFragment)
                .commit()
    }
}