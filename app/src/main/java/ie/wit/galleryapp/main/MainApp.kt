package ie.wit.galleryapp.main

import android.app.Application
import ie.wit.galleryapp.models.GalleryModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val gallerys = ArrayList<GalleryModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Gallery started")
       // gallerys.add(GalleryModel("One", "About one..."))
       // gallerys.add(GalleryModel("Two", "About two..."))
       // gallerys.add(GalleryModel("Three", "About three..."))

    }
}

