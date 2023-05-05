package ie.wit.galleryapp.views.gallerylist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.wit.galleryapp.activities.GalleryMapsActivity
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel
import ie.wit.galleryapp.views.gallery.GalleryView

class GalleryListPresenter(val view: GalleryListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getGallery() = app.gallerys.findAll()

    fun doAddGallery() {
        val launcherIntent = Intent(view, GalleryView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditGallery(gallery: GalleryModel, pos: Int) {
        val launcherIntent = Intent(view, GalleryView::class.java)
        launcherIntent.putExtra("gallery_edit", gallery)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowGalleryMap() {
        val launcherIntent = Intent(view, GalleryMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {  }
    }
}
