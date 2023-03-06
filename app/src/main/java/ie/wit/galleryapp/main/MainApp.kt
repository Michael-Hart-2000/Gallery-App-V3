package ie.wit.gallery.main

import android.app.Application
import ie.wit.galleryapp.models.GalleryMemStore
// import org.wit.gallery.models.GalleryModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val gallerys = GalleryMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Gallery started")
    }
}


