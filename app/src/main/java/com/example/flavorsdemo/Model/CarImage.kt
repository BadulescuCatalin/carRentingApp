package com.example.flavorsdemo.Model

import android.net.Uri

data class CarImage (
    var mainImage: Uri,
    var imageList: List<Uri>
)
{
    constructor() : this(Uri.EMPTY, emptyList())
}