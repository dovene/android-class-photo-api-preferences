package com.dovene.androidhttprequestsharedprefences

import android.content.Context
import android.util.Log
import com.google.gson.Gson

class SharedPreferencesManager {
    companion object {
        const val photoListKey = "photoListKey"
        const val searchKey = "searchKey"
        val preferencesFile = "preferencesFile"
    }

     fun saveSearchCriteria(search:String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString(searchKey, search)
            .apply()
    }

     fun getSearchCriteria(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        return sharedPreferences.getString(searchKey,"")
    }

    fun getLocalPhotoStorage(context: Context): LocalPhotoStorage {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString(photoListKey, "")
        if (json.isNullOrEmpty()) {
            return LocalPhotoStorage(mutableListOf())
        }
        return gson.fromJson(json, LocalPhotoStorage::class.java)
    }

    fun savePhoto(photo: Photos, context: Context) {
        var localPhotoStorage = getLocalPhotoStorage(context)
        localPhotoStorage.localPhotos.add(photo)
        Log.d("savePhoto", " size "+localPhotoStorage.localPhotos.size)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localPhotoStorage)
        sharedPreferences.edit().putString(photoListKey, json).apply()
    }
}