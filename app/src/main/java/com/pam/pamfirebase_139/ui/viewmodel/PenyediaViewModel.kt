package com.pam.pamfirebase_139.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pam.pamfirebase_139.MahasiswaApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiMahasiswa().containerApp.repositoryMhs) }
        initializer { DetailViewModel(aplikasiMahasiswa().containerApp.repositoryMhs) }
        initializer { InsertViewModel(aplikasiMahasiswa().containerApp.repositoryMhs) }
        initializer { UpdateViewModel(aplikasiMahasiswa().containerApp.repositoryMhs) }

    }
}

fun CreationExtras.aplikasiMahasiswa(): MahasiswaApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApp)
