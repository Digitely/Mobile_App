package com.example.sevahandsversionone

class ImageModel {
    var imageUrl: String? = null
        private set

    constructor()
    constructor(imageUrl: String?) {
        this.imageUrl = imageUrl
    }
}
