package inc.software.wifimorfi.az_soft_project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import inc.software.wifimorfi.az_soft_project.Models.DockManager
import inc.software.wifimorfi.az_soft_project.Util.Init

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "Main_paige"
        setSupportActionBar(toolbar)
        // Just a Clear Begining And Way Throw ...
        // Do you Know ? Why is it so slow
        //Init.Toas( applicationContext,getExternalStorageState().toString())

        DockManager.scan_memory(Environment.getExternalStorageDirectory() , applicationContext)

        /*val session = Repository.GetInstant(applicationContext)
        val current_docks = session.dockDao.queryBuilder()
                .where(DockDao.Properties.Fullpath.eq("*"))
                .list()
        Docks_RecyclerAdapter.Init(current_docks , this);*/

        Init.Kot_Ja_main(applicationContext , this)



}

    fun open_topsheet(item: MenuItem) {
        Init.Kot_start_pop(this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bar_menue, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


}
