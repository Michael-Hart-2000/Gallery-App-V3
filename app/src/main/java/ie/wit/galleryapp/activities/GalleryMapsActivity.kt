package ie.wit.galleryapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

import ie.wit.galleryapp.databinding.ActivityGalleryMapsBinding
import ie.wit.galleryapp.databinding.ContentGalleryMapsBinding
import ie.wit.galleryapp.main.MainApp

class GalleryMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityGalleryMapsBinding
    private lateinit var contentBinding: ContentGalleryMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGalleryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        app = application as MainApp
        contentBinding = ContentGalleryMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.gallerys.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //val gallery = marker.tag as GalleryModel
        val tag = marker.tag as Long
        val gallery = app.gallerys.findById(tag)
        contentBinding.currentTitle.text = gallery!!.title
        contentBinding.currentAge.text = gallery.age.toString()
        Picasso.get().load(gallery.image).into(contentBinding.currentImage)
        return false
    }


}

