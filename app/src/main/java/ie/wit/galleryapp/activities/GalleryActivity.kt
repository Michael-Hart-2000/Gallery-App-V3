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
import ie.wit.galleryapp.models.Location


class GalleryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityGalleryBinding
    private var gallery = GalleryModel()
    private lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false
    // var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit = true
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
            showImagePicker(imageIntentLauncher,this)
        }

        registerImagePickerCallback()

        binding.galleryLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (gallery.zoom != 0f) {
                location.lat =  gallery.lat
                location.lng = gallery.lng
                location.zoom = gallery.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerMapCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gallery, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.gallerys.delete(gallery)
                finish()
            }        R.id.item_cancel -> { finish() }
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

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            gallery.image = image

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


    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            ("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            ("Location == $location")
                            gallery.lat = location.lat
                            gallery.lng = location.lng
                            gallery.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


}


