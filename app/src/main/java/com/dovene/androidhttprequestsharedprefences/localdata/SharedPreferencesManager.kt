package com.dovene.androidhttprequestsharedprefences.localdata

import android.content.Context
import android.util.Log
import com.dovene.androidhttprequestsharedprefences.model.LocalPhotoStorage
import com.dovene.androidhttprequestsharedprefences.model.Photos
import com.google.gson.Gson

class SharedPreferencesManager {
    companion object {
        const val photoListKey = "photoListKey"
        const val searchKey = "searchKey"
        const val preferencesFile = "preferencesFile"
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

    fun savePhoto(photo: Photos, context: Context): Boolean {
        var localPhotoStorage = getLocalPhotoStorage(context)
        if (localPhotoStorage.localPhotos.contains(photo)) {
            return false
        }
        localPhotoStorage.localPhotos.add(photo)
        Log.d("savePhoto", " size "+localPhotoStorage.localPhotos.size)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localPhotoStorage)
        sharedPreferences.edit().putString(photoListKey, json).apply()
        return true
    }

    fun deletePhoto(photo: Photos, context: Context) {
        var localPhotoStorage = getLocalPhotoStorage(context)
        localPhotoStorage.localPhotos.remove(photo)
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(localPhotoStorage)
        sharedPreferences.edit().putString(photoListKey, json).apply()
    }
}