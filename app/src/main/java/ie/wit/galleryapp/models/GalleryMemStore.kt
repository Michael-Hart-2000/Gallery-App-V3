package ie.wit.galleryapp.models

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

    override fun delete(gallery: GalleryModel) {
        gallerys.remove(gallery)
    }


    private fun logAll() {
        gallerys.forEach { ("$it") }
    }
}

