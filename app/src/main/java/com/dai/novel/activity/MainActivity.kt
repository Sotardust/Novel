package com.dai.novel.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dai.novel.R
import com.dai.novel.activity.booklist.ChapterListActivity
import com.dai.novel.adapter.BaseRecyclerAdapter
import com.dai.novel.adapter.BookListAdapter
import com.dai.novel.adapter.inter.XTabLayoutListenerAdapter
import com.dai.novel.database.BookList
import com.dai.novel.database.SQLiteOpenHelper
import com.dai.novel.fragment.bookcase.BookcaseFragment
import com.dai.novel.fragment.setting.SettingFragment
import com.dai.novel.util.OkHttpUtils
import com.dai.novel.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*


class MainActivity : BaseActivity() {

    private var tabsTitle: Array<String>? = null
    private var fragmentManager: FragmentManager? = null
    private var tabLayout: TabLayout? = null
    private var bookcaseFragment: BookcaseFragment? = null
    private var settingFragment: SettingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabsTitle = resources.getStringArray(R.array.novel_tab)

        initTabTitle()
        initFragment()
        println("**********************")
        val adapter = BookListAdapter()
        val nameList = ArrayList<String>()
        val titleList = ArrayList<String>()
        bookListRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        bookListRecycler.addItemDecoration(SpaceItemDecoration(2))
        val list = SQLiteOpenHelper(applicationContext).getAllBookListData()
        for (jsonObject in list) {
            nameList.add(jsonObject.getString(BookList.BOOK_NAME))
            titleList.add(jsonObject.getString(BookList.TITLE))
        }
        adapter.data = nameList
        adapter.title = titleList
        bookListRecycler.adapter = adapter
        adapter.setOnItemClickLister(object : BaseRecyclerAdapter.OnItemClickLister<String> {
            override fun onItemClick(position: Int, data: String) {
                println("data = ${data}")
                toast(data)
                val intent = Intent().setClass(applicationContext, ChapterListActivity::class.java)
                intent.putExtra("tableName", data)
                startActivity(intent)
            }

        })
        adapter.setOnItemLongClickLister(object : BaseRecyclerAdapter.OnItemLongClickLister<String> {
            override fun onItemLongClick(position: Int, data: String) {
            }
        })

        addOrUpdate.setOnClickListener {
            val intent = Intent().setClass(this, ObtainNovelActivity::class.java)
            intent.putStringArrayListExtra("name", nameList)
            intent.putStringArrayListExtra("title", titleList)
            startActivity(intent)
        }
    }


    //初始化fragment
    fun initFragment() {
        bookcaseFragment = BookcaseFragment().newInstance()
        settingFragment = SettingFragment().newInstance()
        changeFragment(bookcaseFragment!!)
    }

    // 初始化底部tab
    private fun initTabTitle() {
        tabLayout = findViewById(R.id.tab_layout) as TabLayout
        if (tabLayout != null) {
            for (i in 0..tabsTitle!!.size - 1) {
                var currentTab = tabLayout?.getTabAt(i)
                if (currentTab == null) {
                    currentTab = tabLayout?.newTab()
                    tabLayout?.addTab(currentTab!!)
                }
                currentTab!!.customView = getTabView(i)
            }
        }
        tabLayout?.addOnTabSelectedListener(object : XTabLayoutListenerAdapter() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateFragmentData(tab.position)
            }
        })
    }

    fun getTabView(position: Int): View {
        val view = layoutInflater.inflate(R.layout.novel_bar_tab, null)
        val title = view.findViewById(R.id.nav_title) as TextView

        val icon = view.findViewById(R.id.nav_icon) as ImageView
        when (position) {
            0 -> icon.setBackgroundResource(R.drawable.tab_record_state_selected)
            1 -> icon.setBackgroundResource(R.drawable.tab_my_state_selected)
        }
        title.text = tabsTitle!![position]
        return view
    }

    /**
     * 刷新fragment

     * @param position
     */
    fun updateFragmentData(position: Int) {
        when (position) {
            0 -> changeFragment(bookcaseFragment!!)
            1 -> changeFragment(settingFragment!!)
        }
    }

    // 切換Fragment
    fun changeFragment(f: Fragment) {
        changeFragment(f, R.id.fragment, false, null)
    }

    private fun changeFragment(f: Fragment, @IdRes containerViewId: Int, init: Boolean, mBundle: Bundle?) {
        if (fragmentManager == null) {
            fragmentManager = supportFragmentManager
        }
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(containerViewId, f)
        if (mBundle != null) {
            f.arguments = mBundle
        }
        ft?.commit()
    }

    private var count = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                count = 0
            }
        }, 3000)
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            count++
            if (count < 2) {
                toast("再按一次退出系统")
                println("super.onKeyDown(keyCode, event) = ${super.onKeyDown(keyCode, event)}")
                return false
            }
        }

        return super.onKeyDown(keyCode, event)
    }
}
