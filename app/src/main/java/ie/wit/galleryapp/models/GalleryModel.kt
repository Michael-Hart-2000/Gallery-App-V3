package ie.wit.galleryapp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryModel(var id: Long = 0,
                        var title: String = "",
                        var age: Int = 0,
                        var origin: String = "",
                        var style: String = "",
                        var artefact: String = "",
                        var isAlive: Boolean = false,
                        var image: Uri = Uri.EMPTY,
                        var lat : Double = 0.0,
                        var lng: Double = 0.0,
                        var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable


