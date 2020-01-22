package com.example.tabapplication.main

import android.app.Application
import io.realm.Realm

class EmoBooksApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        Realm.init(this)
    }
}