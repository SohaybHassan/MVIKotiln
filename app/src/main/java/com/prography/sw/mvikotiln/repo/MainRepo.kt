package com.prography.sw.mvikotiln.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.prography.sw.mvikotiln.api.MyRetrofitBuilder
import com.prography.sw.mvikotiln.model.BlogPost
import com.prography.sw.mvikotiln.model.User
import com.prography.sw.mvikotiln.network.*
import com.prography.sw.mvikotiln.ui.main.state.MainViewState

object MainRepo {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userID: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.getUser(userID)
            }

        }.asLiveData()
    }

}