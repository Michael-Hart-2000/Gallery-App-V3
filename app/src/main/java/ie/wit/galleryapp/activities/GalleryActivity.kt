package ie.wit.galleryapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import ie.wit.galleryapp.models.GalleryModel
import timber.log.Timber
import timber.log.Timber.i


class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    var gallery = GalleryModel()
    val gallerys = ArrayList<GalleryModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Gallery Activity started...")

        binding.btnAdd.setOnClickListener() {
            gallery.title = binding.galleryTitle.text.toString()
            gallery.description = binding.description.text.toString()
            if (gallery.title.isNotEmpty()) {
                gallerys.add(gallery.copy())
                i("add Button Pressed: $gallery.title")
                for (i in gallerys.indices)
                { i("Gallery[$i]:${this.gallerys[i]}") }

            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}
