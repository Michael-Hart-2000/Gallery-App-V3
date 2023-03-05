package ie.wit.galleryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.galleryapp.databinding.ActivityGalleryListBinding
import ie.wit.galleryapp.databinding.CardGalleryBinding
import ie.wit.galleryapp.main.MainApp
import ie.wit.galleryapp.models.GalleryModel


class GalleryListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGalleryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = GalleryAdapter(app.gallerys)
    }
}

class GalleryAdapter constructor(private var gallerys: List<GalleryModel>) :
    RecyclerView.Adapter<GalleryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGalleryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val gallery = gallerys[holder.adapterPosition]
        holder.bind(gallery)
    }

    override fun getItemCount(): Int = gallerys.size

    class MainHolder(private val binding : CardGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gallery: GalleryModel) {
            binding.galleryTitle.text = gallery.title
            binding.description.text = gallery.description
        }
    }
}

