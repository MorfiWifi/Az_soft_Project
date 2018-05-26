package inc.software.wifimorfi.az_soft_project

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import inc.software.wifimorfi.az_soft_project.Models.DockManager
import inc.software.wifimorfi.az_soft_project.Ui.FireMissilesDialogFragment
import inc.software.wifimorfi.az_soft_project.Ui.TopSheetActivity
import inc.software.wifimorfi.az_soft_project.Util.Init
import java.security.Permission

class MainActivity : AppCompatActivity() {
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


    }
}
