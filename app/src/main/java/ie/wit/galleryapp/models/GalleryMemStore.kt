package ie.wit.galleryapp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class GalleryMemStore : GalleryStore {

    val gallerys = ArrayList<GalleryModel>()

    override fun findAll(): List<GalleryModel> {
        return gallerys
    }

    override fun create(gallery: GalleryModel) {
        gallery.id = getId()
        gallerys.add(gallery)
        logAll()
    }

    override fun update(gallery: GalleryModel) {
        var foundGallery: GalleryModel? = gallerys.find { p -> p.id == gallery.id }
        if (foundGallery != null) {
            foundGallery.title = gallery.title
            foundGallery.description = gallery.description
            logAll()
        }
    }

    private fun logAll() {
        gallerys.forEach { i("$it") }
    }
}

