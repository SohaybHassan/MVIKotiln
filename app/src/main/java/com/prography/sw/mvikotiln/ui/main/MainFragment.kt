package com.prography.sw.mvikotiln.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.prography.sw.mvikotiln.MainRecyclerAdapter
import com.prography.sw.mvikotiln.R
import com.prography.sw.mvikotiln.model.User
import com.prography.sw.mvikotiln.ui.DataStateListener
import com.prography.sw.mvikotiln.ui.main.state.MainStateEvent
import com.prography.sw.mvikotiln.ui.main.state.MainStateEvent.*
import com.prography.sw.mvikotiln.util.TopSpacingItemDecoration
import com.prography.sw.mvikotiln.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    lateinit var dataStateHandler: DataStateListener
    lateinit var mainRecyclerAdapter: MainRecyclerAdapter
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
        initRecyclerView()

    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            mainRecyclerAdapter = MainRecyclerAdapter()
            adapter = mainRecyclerAdapter
        }
    }

    private fun setUserData(user: User) {
        email.text = user.email
        username.text = user.name
        view?.let {
            Glide.with(it.context)
                .load(user.image).into(image)
        }
    }


    fun startDataStateProcess() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)
            //Handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let { blogPost ->
                        println("DEBUG: DataState: ${blogPost}")
                        viewModel.setBlogListData(blogPost)
                    }
                    mainViewState.user?.let { user ->
                        println("DEBUG: DataState: ${user}")
                        viewModel.SetUser(user)

                    }
                }
            }

        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer { ViewState ->
            ViewState.blogPosts?.let { blogBost ->
                println("DEBUG: Setting bolg Post to RecyclerView: ${blogBost}")
                mainRecyclerAdapter.submitList(blogBost)
            }
            ViewState.user?.let { user ->
                println("DEBUG: Setting user Data: ${user}")
                setUserData(user)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }
}