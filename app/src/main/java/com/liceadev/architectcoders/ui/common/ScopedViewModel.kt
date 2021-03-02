package com.liceadev.architectcoders.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class ScopedViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(), Scope by Scope.ScopeImpl(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}