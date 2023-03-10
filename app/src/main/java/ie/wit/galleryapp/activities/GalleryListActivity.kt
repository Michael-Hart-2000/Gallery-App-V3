package ie.wit.galleryapp.activities


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.galleryapp.R
import ie.wit.galleryapp.adapters.GalleryAdapter
import ie.wit.galleryapp.adapters.GalleryListener
import ie.wit.galleryapp.databinding.ActivityGalleryListBinding
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel


class GalleryListActivity : AppCompatActivity(), GalleryListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGalleryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
      //  binding.recyclerView.adapter = GalleryAdapter(app.gallerys)
        binding.recyclerView.adapter = GalleryAdapter(app.gallerys.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, GalleryActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.gallerys.findAll().size)
            }
        }

    override fun onGalleryClick(gallery: GalleryModel) {
        val launcherIntent = Intent(this, GalleryActivity::class.java)
        launcherIntent.putExtra("gallery_edit", gallery)
        getClickResult.launch(launcherIntent)
    }


    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.gallerys.findAll().size)
            }
        }

}


