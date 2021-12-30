package com.prography.sw.mvikotiln.api

import androidx.lifecycle.LiveData
import com.prography.sw.mvikotiln.model.BlogPost
import com.prography.sw.mvikotiln.model.User
import com.prography.sw.mvikotiln.network.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>
}