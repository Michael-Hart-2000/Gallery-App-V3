package ie.wit.galleryapp.models

import timber.log.Timber.i

class GalleryMemStore : GalleryStore {

    val gallerys = ArrayList<GalleryModel>()

    override fun findAll(): List<GalleryModel> {
        return gallerys
    }

    override fun create(gallery: GalleryModel) {
        gallerys.add(gallery)
        logAll()
    }

    fun logAll() {
        gallerys.forEach{ i("${it}")}
    }
}