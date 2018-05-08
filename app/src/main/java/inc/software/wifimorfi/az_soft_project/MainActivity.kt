package inc.software.wifimorfi.az_soft_project

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import inc.software.wifimorfi.az_soft_project.Models.DockManager
import inc.software.wifimorfi.az_soft_project.Models.DockManager.getExternalStorageState
import inc.software.wifimorfi.az_soft_project.Models.Repository
import inc.software.wifimorfi.az_soft_project.Util.Init
import inc.software.wifimorfi.az_soft_project.View.Docks_RecyclerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
setContentView(R.layout.activity_main)
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
}
