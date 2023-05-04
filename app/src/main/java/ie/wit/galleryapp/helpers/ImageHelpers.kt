package ie.wit.galleryapp.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.wit.galleryapp.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_gallery_image.toString())
    intentLauncher.launch(chooseFile)
}