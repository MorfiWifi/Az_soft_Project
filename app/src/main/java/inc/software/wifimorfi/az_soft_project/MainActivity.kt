package inc.software.wifimorfi.az_soft_project

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import inc.software.wifimorfi.az_soft_project.Models.DockManager
import inc.software.wifimorfi.az_soft_project.Util.Init


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null){
            Init.filter_docks(this , newText)
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    enum class permission{ HAVE , NOPE}

    private var permis = MainActivity.permission.NOPE
    private  val STORAGE_CODE = 56 // this is Storage

    override fun onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext.checkSelfPermission( android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions( arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_CODE)
                permis = MainActivity.permission.NOPE
            } else {
                //We have the permission here //todo add Thr back ground that have no permission
                permis = MainActivity.permission.HAVE
                normal_continue()
            }
        }

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = "کتابخانه"
        setSupportActionBar(toolbar)

        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) ) {
            DockManager.scan_memory(Environment.getExternalStorageDirectory() , applicationContext)
            Init.Kot_Ja_main(applicationContext , this)
        }


        if (permis == permission.HAVE){
            Init.check_recived_list(this) // run in back ground cheking !! 500 mill
        }


        Init.init_search(this)
        val rootView = findViewById(R.id.lin_main_activity)
        rootView.requestFocus()
        //searchView = (SearchView) findViewById(R.id.search_view);
      /*  val m = findViewById(R.id.searchBar)
        m.setOnClickListener()*/

    }

    fun normal_continue (){
        DockManager.scan_memory(Environment.getExternalStorageDirectory() , applicationContext)
        Init.Kot_Ja_main(applicationContext , this)
    }
    fun open_topsheet(item: MenuItem) {
        //TopSheetActivity.mainAC = this // but Why ?
        Init.Kot_start_pop(this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bar_menue, menu)
        // Inflate the menu; this adds items to the action bar if it is present.


        val searchItem = menu.findItem(R.id.searchBar_thing)

        val searchView = searchItem.actionView as SearchView
        searchView.setQueryHint("جستجو")
        searchView.setOnQueryTextListener(this)
        searchView.setIconified(true)
        //searchView.set


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Init.REQ_COD){
            //val gson : Gson
            val data =  data?.getStringExtra(Init.Extra_Kry )
            Init.MainAC_setting = data
        }



        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        
        if (requestCode == STORAGE_CODE ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                normal_continue()
            }else{
                Init.Toas(applicationContext , "نده ..")
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        Init.Save_String(this , Init.Extra_Kry , Init.NO_THING)
        //Init.terminal("MAIN ACTIVITY has Destroyed!!!")
        super.onDestroy()
    }

    fun top_refresh(item: MenuItem) {


        if (permis == permission.HAVE){
            normal_continue()
        }

    }

    override fun onResume() {
        super.onResume()
        //val searchView =  findViewById(R.id.searchBar_thing)
        //searchView.clearFocus()
        val rootView = findViewById(R.id.lin_main_activity)
        rootView.requestFocus()
    }
}
