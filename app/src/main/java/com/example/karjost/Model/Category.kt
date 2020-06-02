package com.example.karjost.Model

class Category {
    var title :String? = null
    var imageUrl: Int? = null

    constructor(title: String, imageUrl: Int){
        this.title = title
        this.imageUrl = imageUrl
    }
}