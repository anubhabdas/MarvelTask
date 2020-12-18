package com.anubhab.das.marveltask.ui.marvel.addMarvel

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.anubhab.das.marveltask.R
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_add_marvel.*

class AddMarvelActivity : AppCompatActivity() {

    private var imageFilePath: String? = null

    companion object {
        public final val EXTRA_MARVEL: String = "com.anubhab.das.marveltask.ui.marvel.addMarvel.EXTRA_MARVEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marvel)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        setTitle("Add Marvel Character")

        add_new_marvel_image.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .crop()
                .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            new_marvel_image.setImageURI(fileUri)

            //You can get File object from intent
            //val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
            imageFilePath = filePath
            Log.d("Image File Path: ", imageFilePath)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.add_marvel_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val marvel_name = edit_text_name.text.toString()
        val marvel_desc = edit_text_description.text.toString()

        if (marvel_name.trim().isEmpty() || marvel_desc.trim().isEmpty() ||
            (imageFilePath!=null && imageFilePath.toString().trim().isEmpty())){
            Toast.makeText(applicationContext, "Please add Name, Description and a Image to save", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        val newMarvel: ArrayList<String?> = arrayListOf<String?>()
        newMarvel.add(marvel_name)
        newMarvel.add(marvel_desc)
        newMarvel.add(imageFilePath)
        data.putStringArrayListExtra(EXTRA_MARVEL, newMarvel)

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}