package com.eagle.rxandroid.vmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eagle.rxandroid.repo.MainRepo

class MainFactoryViewModel(private val repo: MainRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}