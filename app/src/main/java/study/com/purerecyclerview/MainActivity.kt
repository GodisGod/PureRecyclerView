package study.com.purerecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var purRecyclerView: PurRecyclerView
    private lateinit var puradpter: PurAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var datas: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        purRecyclerView = findViewById(R.id.pur_recycler_view)
        datas = ArrayList()
        for (i in 0..10) {
            datas.add("i = $i")
        }
        puradpter = PurAdapter(this, datas)
        purRecyclerView.adapter = puradpter
        purRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

}
