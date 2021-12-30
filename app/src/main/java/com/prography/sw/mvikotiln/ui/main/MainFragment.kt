package com.prography.sw.mvikotiln.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prography.sw.mvikotiln.R
import com.prography.sw.mvikotiln.ui.main.state.MainStateEvent
import com.prography.sw.mvikotiln.ui.main.state.MainStateEvent.*
import com.prography.sw.mvikotiln.viewModel.MainViewModel

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        startDataStateProcess()
    }

    fun startDataStateProcess() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: DataState${dataState}")
            //Handle Data<T>
            dataState.data?.let { mainViewState ->
                mainViewState.blogPosts?.let { blogPost ->
                    viewModel.setBlogListData(blogPost)

                }
                mainViewState.user?.let { user ->
                    viewModel.SetUser(user)

                }
            }
            //Handle Erroe<T>
            dataState.message?.let {

            }
            //Handle Loading<T>
            dataState.loading.let {

            }
        })


        viewModel.viewState.observe(viewLifecycleOwner, Observer { ViewState ->
            ViewState.blogPosts?.let {
                println("DEBUG: Setting bolg Post to RecyclerView: ${it}")
            }
            ViewState.user?.let {
                println("DEBUG: Setting user Data: ${it}")
                //set User Data
            }

        })


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blogs -> triggerGetBlogEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }
}