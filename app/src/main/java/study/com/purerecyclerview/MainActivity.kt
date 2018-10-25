package study.com.purerecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var purRecyclerView: PurRecyclerView
    private lateinit var puradpter: PurAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var datas: ArrayList<String>
    private lateinit var btnAdd: Button
    private var isAdd: Boolean = false
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

        var btn: Button = findViewById(R.id.btn_1)
        btn.setOnClickListener {
            puradpter.setShowHeaderView(false)
            puradpter.notifyDataSetChanged()
        }

    }

}
