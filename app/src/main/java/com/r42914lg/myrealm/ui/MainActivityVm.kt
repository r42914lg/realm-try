package com.r42914lg.myrealm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.r42914lg.myrealm.domain.Item
import com.r42914lg.myrealm.domain.Loader
import kotlinx.coroutines.launch

class MainActivityVm(
    private val loader: Loader<List<Item>>,
) : ViewModel() {

    val itemState = loader.state

    init {
        load()
    }

    fun onAction(event: MainActivityEvent) {
        when (event) {
            MainActivityEvent.Load -> load()
            MainActivityEvent.PullToRefresh -> pullToRefresh()
            MainActivityEvent.ResetAndLoad -> resetAndLoad()
        }
    }

    private fun load() {
        viewModelScope.launch {
            loader.load()
        }
    }

    private fun resetAndLoad() {
        viewModelScope.launch {
            loader.resetAndLoad()
        }
    }

    private fun pullToRefresh() {
        viewModelScope.launch {
            loader.pullToRefresh()
        }
    }

    override fun onCleared() {
        super.onCleared()
        loader.onClear()
    }

    sealed interface UiState {
        object Loading : UiState
        object Error : UiState
        class RenderItems(val data: List<Item>) : UiState
    }
}