package com.pam.pamfirebase_139.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.pam.pamfirebase_139.repository.NetworkRepository
import com.pam.pamfirebase_139.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class MahasiswaContainer(context: Context) : InterfaceContainerApp {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    override val repositoryMhs: RepositoryMhs by lazy {
        NetworkRepository(firestore)
    }
}
