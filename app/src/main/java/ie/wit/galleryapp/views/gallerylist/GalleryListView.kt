package ie.wit.galleryapp.views.gallerylist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.galleryapp.R
import ie.wit.galleryapp.adapters.GalleryAdapter
import ie.wit.galleryapp.adapters.GalleryListener
import ie.wit.galleryapp.databinding.ActivityGalleryListBinding
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel

class GalleryListView : AppCompatActivity(), GalleryListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGalleryListBinding
    private lateinit var presenter: GalleryListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = GalleryListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadGallery()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddGallery() }
            R.id.item_map -> { presenter.doShowGalleryMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGalleryClick(gallery: GalleryModel, position: Int) {
        this.position = position
        presenter.doEditGallery(gallery, this.position)
    }

    private fun loadGallery() {
        binding.recyclerView.adapter = GalleryAdapter(presenter.getGallery(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getGallery().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}
