package com.dovene.androidhttprequestsharedprefences.ui

import com.dovene.androidhttprequestsharedprefences.model.Photos

interface PhotoItemCallback {
    fun onCellClick(photo: Photos)
    fun onSavePhoto(photo: Photos)
}