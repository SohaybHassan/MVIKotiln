package com.prography.sw.mvikotiln.ui

import com.prography.sw.mvikotiln.network.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)

}