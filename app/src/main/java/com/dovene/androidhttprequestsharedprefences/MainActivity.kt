package com.dovene.androidhttprequestsharedprefences

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dovene.androidhttprequestsharedprefences.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        val photographerKey = "photographerKey"
        val imageKey = "imageKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {

        val storedSearch = SharedPreferencesManager().getSearchCriteria(this)
        if (storedSearch != null) {
            binding.searchEt.setText(storedSearch)
        }

        binding.searchBt.setOnClickListener {
            checkUserInput()
            callService()
            binding.searchBt.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
        }
        binding.favoritesBt.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    private fun checkUserInput() {
        if (binding.searchEt.text.toString().isEmpty()) {
            Toast.makeText(this, "Veuillez effectuer une saisie", Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun callService() {
        val service: PhotoApi.PhotoService =
            PhotoApi().getClient().create(PhotoApi.PhotoService::class.java)
        val call: Call<PhotoApiResponse> =
            service.getPhotos(
                "IlQOgCrui5R9g9aHW8r3Frhk78vqlbwWeaYmaMG25eJtzJtCOZyUdNEw",
                binding.searchEt.text.toString()
            )
        call.enqueue(object : Callback<PhotoApiResponse> {
            override fun onResponse(
                call: Call<PhotoApiResponse>,
                response: Response<PhotoApiResponse>
            ) {
                processResponse(response)
                searchEnded()
            }

            override fun onFailure(call: Call<PhotoApiResponse>, t: Throwable) {
                processFailure(t)
                searchEnded()
            }
        })
    }

    private fun searchEnded() {
        binding.searchBt.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
        SharedPreferencesManager().saveSearchCriteria(binding.searchEt.text.toString(), this)
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
    }

    private fun processResponse(response: Response<PhotoApiResponse>) {
        if (response.body() != null) {
            val body = response.body()
            if (body?.photos?.isNotEmpty() == true) {
                val adapter = PhotoListViewAdapter(body.photos, object : PhotoItemCallback {
                    override fun onCellClick(photo: Photos) {
                        gotoNextActivity(photo)
                    }

                    override fun onSavePhoto(photo: Photos) {
                        savePhoto(photo)
                    }

                })
                val recyclerView = findViewById<RecyclerView>(R.id.photo_rv)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            }
        }
    }

    private fun savePhoto(photo: Photos) {
        SharedPreferencesManager().savePhoto(photo, this)
    }

    private fun gotoNextActivity(photo: Photos) {
        val intent = Intent(this, PhotoDetailActivity::class.java)
        intent.putExtra(photographerKey, photo.photographer)
        intent.putExtra(imageKey, photo.src?.original)
        startActivity(intent)
    }
}