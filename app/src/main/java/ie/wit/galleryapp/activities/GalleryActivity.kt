package ie.wit.galleryapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.galleryapp.R
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import ie.wit.galleryapp.helpers.showImagePicker
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel


class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private var gallery = GalleryModel()
    private lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

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
            binding.origin.setText(gallery.origin)
            binding.style.setText(gallery.style)
            binding.artefact.setText(gallery.artefact)
            binding.isAlive.setText(gallery.isAlive.toString())
            binding.btnAdd.setText(R.string.save_gallery)
            Picasso.get()
                .load(gallery.image)
                .into(binding.galleryImage)
            if (gallery.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_gallery_image)
            }
        }

        binding.btnAdd.setOnClickListener {
            gallery.title = binding.galleryTitle.text.toString()
            gallery.description = binding.description.text.toString()
            gallery.origin = binding.origin.text.toString()
            gallery.style = binding.style.text.toString()
            gallery.artefact = binding.artefact.text.toString()
            gallery.isAlive = binding.isAlive.text.toString().toBoolean()
            if (gallery.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_gallery_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.gallerys.update(gallery.copy())
                } else {
                    app.gallerys.create(gallery.copy())
                }
            }
            ("add Button Pressed: $gallery")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()

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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            ("Got Result ${result.data!!.data}")
                            gallery.image = result.data!!.data!!
                            Picasso.get()
                                .load(gallery.image)
                                .into(binding.galleryImage)
                            binding.chooseImage.setText(R.string.change_gallery_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}


