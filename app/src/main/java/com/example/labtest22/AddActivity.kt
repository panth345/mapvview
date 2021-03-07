package com.example.labtest22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.demo.roomdemo.MainActivityViewModel
import com.demo.roomdemo.db.UserEntity
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_edit.*

class AddActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    // var title = ""
    var check = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val toolbar: Toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar)

        //55.952839 , -96.078278
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupViewModel()
        getIntentData()

        val button:Button = findViewById(R.id.update_btn)
        button.setOnClickListener(View.OnClickListener {
           // deleteBookmark()
            val bookmark = UserEntity()
            bookmark.title = updatetitle.text.toString()
            bookmark.subtitle = updatessubtitle.text.toString()
            bookmark.latitude = updatelatitude.text.toString().toDouble()
            bookmark.longitude = updatelongitude.text.toString().toDouble()
            mainActivityViewModel.insertUserInfo(bookmark)
            //  Toast.makeText(applicationContext,"Bookmark Added",Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext,"Bookmark updated", Toast.LENGTH_LONG).show()
var i = Intent(this,MapsActivity::class.java)
            startActivity(i)
        })
    }


    private fun setupViewModel() {
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getAllUsersObservers()
    }

    private fun getIntentData() {
        check = intent.getStringExtra("check").toString()
       // title = intent.getStringExtra("title").toString()
        // UI display data
        updatetitle.setText(intent.getStringExtra("title"))
        updatessubtitle.setText(intent.getStringExtra("subtitle"))
        updatelatitude.setText(intent.getStringExtra("latitude"))
        updatelongitude.setText(intent.getStringExtra("longitude"))

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.update_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemid=item.itemId

        if (itemid.equals(R.id.delete))
        {
            deleteBookmark()
            return true
        }
else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun deleteBookmark() {
        var list = mainActivityViewModel.getAllUsers()
        for (i in list!!) {
            if (i.title == updatetitle.text.toString()) {
                mainActivityViewModel.deleteUserInfo(i)
                Toast.makeText(applicationContext,"Bookmark Deleted",Toast.LENGTH_LONG).show()
                val inte = Intent(this,MapsActivity::class.java)
                startActivity(inte)
            }
        }
    }

    private fun updateBookmark() {

        if (check.equals("true")) {
            var list = mainActivityViewModel.getAllUsers()
            for (i in list!!) {
                if (i.title == title)
                    i.title = titleinput.text.toString()
                i.subtitle = titleinput.text.toString()
                i.latitude = titleinput.text.toString().toDouble()
                i.longitude = titleinput.text.toString().toDouble()
                mainActivityViewModel.updateUserInfo(i)
               // mainActivityViewModel.deleteUserInfo(i)


            }
        }
    }
}



