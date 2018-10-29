package study.com.purerecyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import study.com.purerecyclerview.lineartest.LinearTestActivity
import study.com.purerecyclerview.lineartest.TestActivity
import study.com.purerecyclerview.test.EnhanceRecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var purRecyclerView: PurRecyclerView

    private lateinit var enRecyclerView: EnhanceRecyclerView
    private lateinit var puradpter: PurAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var datas: ArrayList<String>
    private lateinit var btnAdd: Button
    private var isAdd: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        purRecyclerView = findViewById(R.id.pur_recycler_view)
        enRecyclerView = findViewById(R.id.en_recycler_view)
        datas = ArrayList()
        for (i in 0..15) {
            datas.add("i = $i")
        }
        puradpter = PurAdapter(this, datas)
        purRecyclerView.adapter = puradpter
        purRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

//        enRecyclerView.adapter = puradpter
//        enRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        purRecyclerView.setRefreshListener(object : RefreshListener {
            override fun startRefresh() {
                Log.i("LHD", "结束刷新")
                Handler().postDelayed(Runnable {
                    purRecyclerView.completeRefresh()
                }, 3000)
            }
        })

        var btn: Button = findViewById(R.id.btn_1)
        btn.setOnClickListener {
            //            puradpter.setShowHeaderView(false)
//            puradpter.notifyDataSetChanged()
            startActivity(Intent(this, TestActivity::class.java))
        }

        var btn2: Button = findViewById(R.id.btn_2)
        btn2.setOnClickListener {
            puradpter.setShowHeaderView(true)
            puradpter.notifyDataSetChanged()
        }

    }

}
