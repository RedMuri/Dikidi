package com.example.dikidi.ui.intent

sealed class HomeIntent {

    data object LoadData: HomeIntent()
    data object ReloadData: HomeIntent()
}