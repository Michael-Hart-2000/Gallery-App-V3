package ie.wit.galleryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.galleryapp.R
import ie.wit.galleryapp.main.MainApp

class GalleryListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_list)
        app = application as MainApp
    }
}
