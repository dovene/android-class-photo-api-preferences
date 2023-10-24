package com.dovene.androidhttprequestsharedprefences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dovene.androidhttprequestsharedprefences.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewItems()
    }

    private fun setViewItems() {
        setTitle(getString(R.string.app_name))
        binding.searchBt.setOnClickListener {
            callService()
            binding.searchBt.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
        }
    }

    private fun callService() {
        val service: PhotoApi.PhotoService =
            PhotoApi().getClient().create(PhotoApi.PhotoService::class.java)
        val searchCriteria = findViewById<EditText>(R.id.search_et).text.toString()
        val call: Call<PhotoApiResponse> =
            service.getPhotos(
                "IlQOgCrui5R9g9aHW8r3Frhk78vqlbwWeaYmaMG25eJtzJtCOZyUdNEw",
                searchCriteria
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
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
    }

    private fun processResponse(response: Response<PhotoApiResponse>) {
        if (response.body() != null) {
            val body = response.body()
            if (!body?.photos.isNullOrEmpty()) {
                val adapter = PhotoListViewAdapter(body?.photos!!)
                val recyclerView = findViewById<RecyclerView>(R.id.photo_rv)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            }
        }
    }
}