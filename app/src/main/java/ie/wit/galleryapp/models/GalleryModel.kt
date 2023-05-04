package ie.wit.galleryapp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryModel(var id: Long = 0,
                        var title: String = "",
                        var description: String = "",
                        var origin: String = "",
                        var style: String = "",
                        var artefact: String = "",
                        var isAlive: Boolean = false,
                        var image: Uri = Uri.EMPTY ) : Parcelable


