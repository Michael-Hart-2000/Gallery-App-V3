package ie.wit.galleryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.galleryapp.R
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel
import timber.log.Timber
import timber.log.Timber.i

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    var gallery = GalleryModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("gallery_edit")) {
            edit = true
            gallery = intent.extras?.getParcelable("gallery_edit")!!
            binding.galleryTitle.setText(gallery.title)
            binding.description.setText(gallery.description)
            binding.btnAdd.setText(R.string.save_gallery)
        }

        binding.btnAdd.setOnClickListener() {
            gallery.title = binding.galleryTitle.text.toString()
            gallery.description = binding.description.text.toString()
            if (gallery.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_gallery_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.gallerys.update(gallery.copy())
                } else {
                    app.gallerys.create(gallery.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
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
