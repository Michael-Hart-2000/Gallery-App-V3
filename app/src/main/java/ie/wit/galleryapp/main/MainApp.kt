package ie.wit.galleryapp.main

import android.app.Application
import ie.wit.galleryapp.models.GalleryJSONStore
import ie.wit.galleryapp.models.GalleryMemStore
import ie.wit.galleryapp.models.GalleryStore
import timber.log.Timber
import timber.log.Timber.Forest.i


class MainApp : Application() {

    lateinit var gallerys: GalleryStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        gallerys = GalleryJSONStore(applicationContext)
        //gallerys = GalleryMemStore()
        i("Gallery started")

       // gallerys.add(GalleryModel("One", "About one..."))
       // gallerys.add(GalleryModel("Two", "About two..."))
       // gallerys.add(GalleryModel("Three", "About three..."))
    }
}

