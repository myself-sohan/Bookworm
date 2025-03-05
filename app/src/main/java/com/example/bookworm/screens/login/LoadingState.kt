package com.example.bookworm.screens.login

import javax.net.ssl.SSLEngineResult

data class LoadingState(
    val status: Status,
    val message : String? = null
)
{
    companion object{
        val IDLE = LoadingState(Status.IDLE)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val FAILED = LoadingState(Status.FAILED)
        val LOADING = LoadingState(Status.LOADING)
    }
    enum class Status{
        SUCCESS,
        FAILED,
        LOADING,
        IDLE
    }
}
