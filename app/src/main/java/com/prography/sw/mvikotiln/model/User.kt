package com.prography.sw.mvikotiln.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("username")
    @Expose
    val name: String? = null,
    @SerializedName("")
    @Expose
    val image: String? = null,
) {
    override fun toString(): String {
        return "User (email=$email, username=$name, image=$image)"
    }
}