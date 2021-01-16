package com.liceadev.architectcoders.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liceadev.architectcoders.model.Scope

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