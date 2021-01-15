package com.liceadev.mymovies.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liceadev.mymovies.model.Scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class CoroutineScopeActivity : AppCompatActivity(), Scope by Scope.ScopeImpl() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScope()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelScope()
    }
}