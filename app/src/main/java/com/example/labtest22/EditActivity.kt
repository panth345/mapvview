package com.example.labtest22

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.demo.roomdemo.MainActivityViewModel
import com.demo.roomdemo.db.UserEntity
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity: AppCompatActivity() {


    private lateinit var mainActivityViewModel: MainActivityViewModel
    var title = ""
   // var check = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        var toolbar: Toolbar = findViewById(R.id.my_toolbar2)
        setSupportActionBar(toolbar)

        //55.952839 , -96.078278
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupViewModel()
        getIntentData()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var itemid=item.itemId
        if (itemid.equals(R.id.save)) {
           addBookmark()
            return true
        }

        else {
            return super.onOptionsItemSelected(item)
        }
    }

   /* private fun deleteBookmark() {
        var list = mainActivityViewModel.getAllUsers()
        for (i in list!!) {
            if (i.title == titleinput.text.toString()) {
                mainActivityViewModel.deleteUserInfo(i)
                Toast.makeText(applicationContext,"Bookmark Deleted",Toast.LENGTH_LONG).show()
            }
        }
    }*/


    private fun setupViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getAllUsersObservers()
    }

    private fun getIntentData() {
        // check = intent.getStringExtra("check").toString()
        title=intent.getStringExtra("title").toString()
            // UI display data
            titleinput.setText(intent.getStringExtra("title"))
            subtitleinput.setText(intent.getStringExtra("subtitle"))
            latitudeinput.setText(intent.getStringExtra("latitude"))
            longitudeinput.setText(intent.getStringExtra("longitude"))

    }

    private fun addBookmark() {

       /* if (check.equals("true")) {
            var list = mainActivityViewModel.getAllUsers()
            for (i in list!!) {
                if (i.title == title)
                    i.title = titleinput.text.toString()
                i.subtitle = titleinput.text.toString()
                i.latitude = titleinput.text.toString().toDouble()
                i.longitude = titleinput.text.toString().toDouble()
                mainActivityViewModel.updateUserInfo(i)
            }
        }*/
       // else {
            val bookmark = UserEntity()
            bookmark.title = titleinput.text.toString()
            bookmark.subtitle = subtitleinput.text.toString()
            bookmark.latitude = latitudeinput.text.toString().toDouble()
            bookmark.longitude = longitudeinput.text.toString().toDouble()
            mainActivityViewModel.insertUserInfo(bookmark)
            Toast.makeText(applicationContext,"Bookmark Added",Toast.LENGTH_LONG).show()
             val i = Intent(this,MapsActivity::class.java)
startActivity(i)

    // }
        //check="false"
    }
}