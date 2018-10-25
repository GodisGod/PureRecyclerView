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
    private lateinit var headerView: HeaderView
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

//        headerView = findViewById(R.id.head_view)

        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            var lp: ViewGroup.LayoutParams = headerView.layoutParams
            lp.height += 100
            headerView.layoutParams = lp
        }

    }

}
