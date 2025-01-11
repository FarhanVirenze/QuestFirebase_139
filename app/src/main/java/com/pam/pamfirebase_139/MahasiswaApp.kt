package com.pam.pamfirebase_139

import android.app.Application
import com.pam.pamfirebase_139.di.MahasiswaContainer

class MahasiswaApp : Application() {

    lateinit var containerApp: MahasiswaContainer

    override fun onCreate() {
        super.onCreate()

        containerApp = MahasiswaContainer(this)
    }
}