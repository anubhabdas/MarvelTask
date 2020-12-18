package com.anubhab.das.marveltask.ui.marvel.listMarvel

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amitshekhar.DebugDB
import com.anubhab.das.marveltask.R
import com.anubhab.das.marveltask.data.repository.MarvelRepository
import com.anubhab.das.marveltask.data.room.Marvel
import com.anubhab.das.marveltask.data.room.MarvelDatabase
import com.anubhab.das.marveltask.ui.marvel.adapter.MarvelAdapter
import com.anubhab.das.marveltask.ui.marvel.addMarvel.AddMarvelActivity
import com.anubhab.das.marveltask.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_marvellist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MarvelListActivity : AppCompatActivity() {

    private val ADD_MARVEL_REQUEST = 1
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MarvelDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MarvelRepository(database.marvelDao()) }
    val utils: Utils = Utils()
    lateinit var marvelViewModel: MarvelListViewModel
    private final val marvelAdapter: MarvelAdapter = MarvelAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marvellist)
        Log.d("DebugDB", DebugDB.getAddressLog())
        Toast.makeText(applicationContext, "Swipe Left to Delete Items", Toast.LENGTH_LONG).show()

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        recyclerview.adapter = marvelAdapter

        marvelViewModel = ViewModelProvider(this,
            MarvelListViewModelFactory(
                repository
            )
        )
            .get(MarvelListViewModel::class.java)

        marvelViewModel.allMarvelCharacter.observe(this, Observer {
            marvelAdapter.setMarvelList(it)
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MarvelListActivity, AddMarvelActivity::class.java)
            startActivityForResult(intent, ADD_MARVEL_REQUEST)
        }

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object:
                ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    marvelViewModel.deleteMarvelChar(marvelAdapter.getMarvelAt(viewHolder.adapterPosition))
                    Toast.makeText(applicationContext, "Marvel Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_MARVEL_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.getStringArrayListExtra(AddMarvelActivity.EXTRA_MARVEL)?.let { marvel ->
                val newMarvel = Marvel(utils.generateRandomInteger(), marvel[0], marvel[1], marvel[2])
                marvelViewModel.insertMarvelChar(newMarvel)
                Toast.makeText(
                    applicationContext,
                    "New Marvel Character named ${marvel[0]} is added",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.delete_all_marvels, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                marvelViewModel.deleteAllMarvelChar()
                Toast.makeText(applicationContext, "All Marvel Characters deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}