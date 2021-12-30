package com.prography.sw.mvikotiln.ui.main.state

import com.prography.sw.mvikotiln.model.BlogPost
import com.prography.sw.mvikotiln.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
) {
}