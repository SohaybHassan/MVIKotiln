package com.prography.sw.mvikotiln.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogPost(
    @SerializedName("pk")
    @Expose
    val pk: Int? = null,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("body")
    @Expose
    val body: String? = null,
    @SerializedName("image")
    @Expose
    val image: String? = null


) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as BlogPost

        if (pk != other.pk) return false

        return true
    }

    override fun toString(): String {
        return "BlogPost(title=$title, body=$body, image=$image)"
    }
}