package ie.wit.galleryapp.views.gallery

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.galleryapp.R
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import ie.wit.galleryapp.models.GalleryModel
import timber.log.Timber.Forest.i

class GalleryView : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private lateinit var presenter: GalleryPresenter
    var gallery = GalleryModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = GalleryPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheGallery( binding.galleryTitle.text.toString(),
                                    binding.description.text.toString(),
                                    binding.origin.text.toString(),
                                    binding.style.text.toString(),
                                    binding.artefact.text.toString(),
                                    binding.isAlive.text.toString().toBoolean())
            presenter.doSelectImage()
        }

        binding.galleryLocation.setOnClickListener {
            presenter.cacheGallery( binding.galleryTitle.text.toString(),
                                    binding.description.text.toString(),
                                    binding.origin.text.toString(),
                                    binding.style.text.toString(),
                                    binding.artefact.text.toString(),
                                    binding.isAlive.text.toString().toBoolean())
            presenter.doSetLocation()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.galleryTitle.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.enter_gallery_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                // presenter.cacheGallery(binding.galleryTitle.text.toString(), binding.description.text.toString())
                presenter.doAddOrSave(  binding.galleryTitle.text.toString(),
                                        binding.description.text.toString(),
                                        binding.origin.text.toString(),
                                        binding.style.text.toString(),
                                        binding.artefact.text.toString(),
                                        binding.isAlive.text.toString().toBoolean())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gallery, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showGallery(gallery: GalleryModel) {
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

    fun updateImage(image: Uri) {
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.galleryImage)
        binding.chooseImage.setText(R.string.change_gallery_image)
    }
}


