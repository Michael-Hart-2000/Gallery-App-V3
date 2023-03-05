package ie.wit.galleryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import timber.log.Timber
import timber.log.Timber.i

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Gallery Activity started...")

        binding.btnAdd.setOnClickListener() {
            val galleryTitle = binding.galleryTitle.text.toString()
            if (galleryTitle.isNotEmpty()) {
                i("add Button Pressed: $galleryTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}
