package ie.wit.galleryapp.main

import android.app.Application
import ie.wit.galleryapp.models.GalleryMemStore
import ie.wit.galleryapp.models.GalleryModel
import timber.log.Timber


class MainApp : Application() {

    // val gallerys = ArrayList<GalleryModel>()
    val gallerys = GalleryMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        ("Gallery started")
       // gallerys.add(GalleryModel("One", "About one..."))
       // gallerys.add(GalleryModel("Two", "About two..."))
       // gallerys.add(GalleryModel("Three", "About three..."))

    }
}

