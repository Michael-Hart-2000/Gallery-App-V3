package ie.wit.galleryapp.models

interface GalleryStore {
    fun findAll(): List<GalleryModel>
    fun create(gallery: GalleryModel)
    fun update(gallery: GalleryModel)
    fun delete(gallery: GalleryModel)
    fun findById(id:Long) : GalleryModel?
}