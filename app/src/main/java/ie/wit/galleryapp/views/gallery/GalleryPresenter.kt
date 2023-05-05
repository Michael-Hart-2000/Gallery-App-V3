package ie.wit.galleryapp.views.gallery

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.wit.galleryapp.databinding.ActivityGalleryBinding
import ie.wit.galleryapp.helpers.showImagePicker
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel
import ie.wit.galleryapp.models.Location
import ie.wit.galleryapp.views.editlocation.EditLocationView
import timber.log.Timber

class GalleryPresenter(private val view: GalleryView) {

    var gallery = GalleryModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityGalleryBinding = ActivityGalleryBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("gallery_edit")) {
            edit = true
            gallery = view.intent.extras?.getParcelable("gallery_edit")!!
            view.showGallery(gallery)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }
    fun doAddOrSave(name: String, age: Int, origin: String, style:String, artefact:String, isAlive:Boolean) {
        gallery.name = name
        gallery.age = age
        gallery.origin = origin
        gallery.style = style
        gallery.artefact = artefact
        gallery.isAlive = isAlive
        if (edit) {
            app.gallerys.update(gallery)
        } else {
            app.gallerys.create(gallery)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }
    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.gallerys.delete(gallery)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }
    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (gallery.zoom != 0f) {
            location.lat =  gallery.lat
            location.lng = gallery.lng
            location.zoom = gallery.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }
    fun cacheGallery (name: String, age: Int, origin: String, style:String, artefact:String, isAlive:Boolean) {
        gallery.name = name;
        gallery.age = age
        gallery.origin = origin
        gallery.style = style
        gallery.artefact = artefact
        gallery.isAlive = isAlive
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            gallery.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(gallery.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(gallery.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }            }    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            gallery.lat = location.lat
                            gallery.lng = location.lng
                            gallery.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }            }    }}
