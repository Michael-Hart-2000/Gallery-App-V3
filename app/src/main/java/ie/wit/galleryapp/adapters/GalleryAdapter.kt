import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.galleryapp.databinding.CardGalleryBinding
import ie.wit.galleryapp.models.GalleryModel

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