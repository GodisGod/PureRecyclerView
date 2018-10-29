package study.com.purerecyclerview.lineartest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import study.com.purerecyclerview.R
import study.com.purerecyclerview.test.EnhanceRecyclerView

class LinearTestActivity : AppCompatActivity() {

    private lateinit var recyclerTest: EnhanceRecyclerView
    private lateinit var purAdapter: TestAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var datas: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_test)
        recyclerTest = findViewById(R.id.recycler_test)
        datas = ArrayList()
        for (i in 0..10) {
            datas.add("i = $i")
        }
        purAdapter = TestAdapter(this, datas)
        recyclerTest.adapter = purAdapter
        recyclerTest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerTest.setPullToRefreshListener { refreshData() }
        recyclerTest.setLoadMoreListener { loadMoreData() }
    }

    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                for (i in 0..1)
                    datas.add(0, "http://img15.3lian.com/2015/a1/16/d/202.jpg")
                recyclerTest.setRefreshComplete()
            }
            if (msg.what == 2) {
                for (i in 0..1)
                    datas.add("http://a.hiphotos.baidu.com/image/pic/item/03087bf40ad162d96270c41b13dfa9ec8a13cd1f.jpg")
                recyclerTest.setLoadMoreComplete()
            }
        }
    }

    fun refreshData() {
        mHandler.sendEmptyMessageDelayed(1, 3000)
    }

    fun loadMoreData() {
        mHandler.sendEmptyMessageDelayed(2, 3000)
    }


}
