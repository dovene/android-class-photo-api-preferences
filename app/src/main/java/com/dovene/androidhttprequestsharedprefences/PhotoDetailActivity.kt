package com.dovene.androidhttprequestsharedprefences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dovene.androidhttprequestsharedprefences.databinding.ActivityPhotoDetailBinding

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        val photographer = intent.extras?.getString(MainActivity.photographerKey)
        val image = intent.extras?.getString(MainActivity.imageKey)
        binding.photographerTv.text = photographer
        Glide.with(this).load(image)
            .into(binding.photoIv)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}