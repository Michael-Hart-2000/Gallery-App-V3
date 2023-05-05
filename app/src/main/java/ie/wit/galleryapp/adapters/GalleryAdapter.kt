package ie.wit.galleryapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.galleryapp.databinding.CardGalleryBinding
import ie.wit.galleryapp.models.GalleryModel

interface GalleryListener {
    fun onGalleryClick(gallery: GalleryModel, position: Int)
}

class GalleryAdapter constructor(private var gallerys: List<GalleryModel>,
                                   private val listener: GalleryListener) :
    RecyclerView.Adapter<GalleryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGalleryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val gallery = gallerys[holder.adapterPosition]
        holder.bind(gallery, listener)
    }

    override fun getItemCount(): Int = gallerys.size

    class MainHolder(private val binding : CardGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gallery: GalleryModel, listener: GalleryListener) {
            binding.galleryTitle.text = gallery.title
            binding.description.text = gallery.description
            binding.origin.text = gallery.origin
            binding.style.text = gallery.style
            binding.artefact.text = gallery.artefact
            binding.isAlive.text = gallery.isAlive.toString()
            Picasso.get().load(gallery.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onGalleryClick(gallery,adapterPosition) }
        }
    }
}
