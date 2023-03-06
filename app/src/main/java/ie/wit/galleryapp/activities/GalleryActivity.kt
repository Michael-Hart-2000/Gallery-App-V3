package ie.wit.galleryapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.wit.gallery.main.MainApp
import ie.wit.galleryapp.R
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
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
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Gallery Activity started...")
        binding.btnAdd.setOnClickListener() {
            gallery.title = binding.galleryTitle.text.toString()
            gallery.description = binding.description.text.toString()
            gallery.origin = binding.origin.text.toString()
            gallery.style = binding.style.text.toString()
            gallery.artefact = binding.artefact.text.toString()

            if (gallery.title.isNotEmpty()) {
               // app.gallerys.add(gallery.copy())
                app.gallerys.create(gallery.copy())
                setResult(RESULT_OK)
                finish()
                }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
