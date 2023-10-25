package com.dovene.androidhttprequestsharedprefences.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.dovene.androidhttprequestsharedprefences.R
import com.dovene.androidhttprequestsharedprefences.localdata.SharedPreferencesManager
import com.dovene.androidhttprequestsharedprefences.databinding.ActivityFavoritesBinding
import com.dovene.androidhttprequestsharedprefences.model.Photos

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(R.string.favorites)
        displayPhotoList(SharedPreferencesManager().getLocalPhotoStorage(this).localPhotos)
    }


    fun displayPhotoList(photos: MutableList<Photos>) {
        val adapter = PhotoListViewAdapter(photos, object : PhotoItemCallback {
            override fun onCellClick(photo: Photos) {
            }
            override fun onSavePhoto(photo: Photos) {
                // here in favorites list we should rather delete
                deletePhoto(photo)
            }
        })
        binding.photoRv.adapter = adapter
        binding.photoRv.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun deletePhoto(photo: Photos) {
        SharedPreferencesManager().deletePhoto(photo, this)
        displayPhotoList(SharedPreferencesManager().getLocalPhotoStorage(this).localPhotos)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}