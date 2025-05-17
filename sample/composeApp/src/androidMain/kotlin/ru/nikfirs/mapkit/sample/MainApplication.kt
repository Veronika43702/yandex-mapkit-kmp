package ru.nikfirs.mapkit.sample

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ru.nikfirs.mapkit.sample.initMapKit()
    }

}