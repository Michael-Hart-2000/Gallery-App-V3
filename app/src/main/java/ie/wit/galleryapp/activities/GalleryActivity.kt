package ie.wit.galleryapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel
import timber.log.Timber
import timber.log.Timber.i


class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    var gallery = GalleryModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Gallery Activity started...")
        binding.btnAdd.setOnClickListener() {
            gallery.title = binding.galleryTitle.text.toString()
            gallery.description = binding.description.text.toString()
            if (gallery.title.isNotEmpty()) {
                app.gallerys.add(gallery.copy())
                i("add Button Pressed: $gallery")
                for (i in app.gallerys.indices)
                { i("Gallery[$i]:${app.gallerys[i]}") }

            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}
