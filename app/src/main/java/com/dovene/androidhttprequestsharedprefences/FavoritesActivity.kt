package com.dovene.androidhttprequestsharedprefences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dovene.androidhttprequestsharedprefences.databinding.ActivityFavoritesBinding
import com.dovene.androidhttprequestsharedprefences.databinding.ActivityMainBinding

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {
        displayPhotoList(SharedPreferencesManager().getLocalPhotoStorage(this).localPhotos)
    }


    fun displayPhotoList(photos: MutableList<Photos>) {
        val adapter = PhotoListViewAdapter(photos, object : PhotoItemCallback {
            override fun onCellClick(photo: Photos) {
            }
            override fun onSavePhoto(photo: Photos) {
            }
        })
        binding.photoRv.adapter = adapter
        binding.photoRv.layoutManager = LinearLayoutManager(applicationContext)
    }
}