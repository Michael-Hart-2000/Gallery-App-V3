package ie.wit.galleryapp.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.galleryapp.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "gallerys.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<GalleryModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GalleryJSONStore(private val context: Context) : GalleryStore {

    var gallerys = mutableListOf<GalleryModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GalleryModel> {
        logAll()
        return gallerys
    }

    override fun create(gallery: GalleryModel) {
        gallery.id = generateRandomId()
        gallerys.add(gallery)
        serialize()
    }

    override fun findById(id:Long) : GalleryModel? {
        val foundGallery: GalleryModel? = gallerys.find { it.id == id }
        return foundGallery
    }

    override fun update(gallery: GalleryModel) {
        val gallerysList = findAll() as ArrayList<GalleryModel>
        var foundGallery: GalleryModel? = gallerysList.find { p -> p.id == gallery.id }
        if (foundGallery != null) {
            foundGallery.title = gallery.title
            foundGallery.age = gallery.age
            foundGallery.origin = gallery.origin
            foundGallery.style = gallery.style
            foundGallery.artefact = gallery.artefact
            foundGallery.isAlive = gallery.isAlive
            foundGallery.image = gallery.image
            foundGallery.lat = gallery.lat
            foundGallery.lng = gallery.lng
            foundGallery.zoom = gallery.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(gallerys, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        gallerys = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(gallery: GalleryModel) {
        gallerys.remove(gallery)
        serialize()
    }

    private fun logAll() {
        gallerys.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
