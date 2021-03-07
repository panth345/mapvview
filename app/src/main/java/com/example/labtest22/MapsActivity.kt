package com.example.labtest22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.demo.roomdemo.MainActivityViewModel
import com.demo.roomdemo.db.UserEntity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var toolbar: Toolbar

    private lateinit var mainActivityViewModel: MainActivityViewModel

    companion object {
        const val EXTRA_BOOKMARK_ID = "com.raywenderlich.placebook.EXTRA_BOOKMARK_ID"
        private const val REQUEST_LOCATION = 1
        private const val AUTOCOMPLETE_REQUEST_CODE = 2
        private const val TAG = "MapsActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        toolbar=findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnInfoWindowClickListener {
            // newBookmark(latLng)
            handleInfoWindowClick(it)
        }
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mMap.clear()
        createBookmarkMarkerObserver()
    }

    private fun createBookmarkMarkerObserver() {
        // 1
        mainActivityViewModel.getAllUsersObservers()?.observe(
                this, Observer {
                //update the map data
                displayAllBookmarks(it)

        })
    }
    //50.037833  -99.691406
    private fun displayAllBookmarks( bookmarks: List<UserEntity>) {

        var sec:String=bookmarks.count().toString()
        Toast.makeText(applicationContext,"Welcome to the map view ", Toast.LENGTH_LONG).show()
        for (bookmark in bookmarks) {
            addPlaceMarker(bookmark)
        }
    }

    private fun addPlaceMarker( bookmark: UserEntity) {
        //adds a single blue marker to the map
        val posi=LatLng(bookmark.latitude,bookmark.longitude)
         mMap.addMarker( MarkerOptions()
            .position(posi)
            .title(bookmark.title)
            .snippet(bookmark.subtitle)
        )
        //mainActivityViewModel.insertUserInfo(boo)

    }

    private fun handleInfoWindowClick(marker: Marker?) {


            /*        //you never save this before
                    is BookmarkDetailsViewModel.PlaceInfo -> {
                        val placeInfo = (marker.tag as PlaceInfo)
                        if (placeInfo.place != null && placeInfo.image != null) {
                            GlobalScope.launch {
                                mapsViewModel.addBookmarkFromPlace(placeInfo.place,
                                    placeInfo.image)
                            }
                        }
                        marker.remove();
                    }*/

            //this is saved

                    startBookmarkDetails(marker)
//createBookmarkMarkerObserver()

    }



    private fun startBookmarkDetails(marker: Marker?) {
        val intent = Intent(this,AddActivity::class.java)
       // val intent = Intent(this, AddActivity::class.java)
        intent.putExtra("title", marker!!.title)
        intent.putExtra("latitude", marker!!.position.latitude.toString())
        intent.putExtra("longitude", marker!!.position.longitude.toString())
        intent.putExtra("subtitle", marker!!.snippet)
        //Toast.makeText(applicationContext,marker.position.latitude.to,Toast.LENGTH_SHORT).s
        intent.putExtra("check","true")
        startActivity(intent)

        createBookmarkMarkerObserver()
        //addPlaceMarker(bookmark = this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemid=item.itemId
        if (itemid==R.id.add) {
            var intent = Intent(applicationContext, EditActivity::class.java)
            intent.putExtra("check1", false)
            startActivity(intent)
        }
            else if( itemid==R.id.normal_map) {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
          else if(itemid==R.id.hybrid_map) {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
         else   if(itemid==R.id.satellite_map) {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
         else  if(itemid==R.id.terrain_map) {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            else  super.onOptionsItemSelected(item)



        return true
    }
}