package com.prography.sw.mvikotiln.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.prography.sw.mvikotiln.model.BlogPost
import com.prography.sw.mvikotiln.model.User
import com.prography.sw.mvikotiln.network.DataState
import com.prography.sw.mvikotiln.repo.MainRepo
import com.prography.sw.mvikotiln.ui.main.state.MainStateEvent
import com.prography.sw.mvikotiln.ui.main.state.MainStateEvent.*
import com.prography.sw.mvikotiln.ui.main.state.MainViewState
import com.prography.sw.mvikotiln.util.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent.let {
                handelStsteEvent(it)
            }
        }


    fun handelStsteEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        when (stateEvent) {
            is GetBlogPostsEvent -> {
                return MainRepo.getBlogPosts()
            }
            is GetUserEvent -> {
                return MainRepo.getUser(stateEvent.userId)
            }
            is None -> {
                return AbsentLiveData.create()
            }
        }
    }


    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun SetUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let { it } ?: MainViewState()
        return value
    }


    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}
