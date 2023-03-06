package ie.wit.galleryapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "") : Parcelable


