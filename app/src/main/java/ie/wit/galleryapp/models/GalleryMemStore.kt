package ie.wit.galleryapp.models

import timber.log.Timber.Forest.i

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
            foundGallery.name = gallery.name
            foundGallery.age = gallery.age
            foundGallery.origin = gallery.origin
            foundGallery.style = gallery.style
            foundGallery.artefact = gallery.artefact
            foundGallery.isAlive = gallery.isAlive
            foundGallery.image = gallery.image
            foundGallery.lat = gallery.lat
            foundGallery.lng = gallery.lng
            foundGallery.zoom = gallery.zoom
            logAll()
        }
    }

    private fun logAll() {
        gallerys.forEach { i("$it") }
    }

    override fun delete(gallery: GalleryModel) {
        gallerys.remove(gallery)
    }

    override fun findById(id:Long) : GalleryModel? {
        val foundGallery: GalleryModel? = gallerys.find { it.id == id }
        return foundGallery
    }
}

