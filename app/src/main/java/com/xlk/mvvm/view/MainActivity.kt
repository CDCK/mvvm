package com.xlk.mvvm.view

import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.xlk.mvvm.ActivityMainBinding
import com.xlk.mvvm.R
import com.xlk.mvvm.ReadConfigBinding
import com.xlk.mvvm.util.LogUtil
import com.xlk.mvvm.viewmodel.MainViewModel
import com.xlk.mvvm.viewmodel.ReadConfigViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mBinding: ActivityMainBinding;
    private lateinit var mViewModel: MainViewModel

    private lateinit var mReadConfigBinding: ReadConfigBinding;
    private lateinit var mReadConfigViewModel: ReadConfigViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = MainViewModel(this)
        mViewModel.today()
        mBinding.vm = mViewModel

        //解决侧滑中icon颜色一直是灰色问题
        navigation_view.itemIconTintList = null
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                //抽屉菜单展示时，将内容界面右移
                val dm = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(dm)
                content_ll.layout(
                    navigation_view.right,
                    0,
                    navigation_view.right + dm.widthPixels,
                    dm.heightPixels
                )
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

        })
        menu_open.setOnClickListener {
            LogUtil.d("cdck", "打开抽屉菜单");
            drawer_layout.openDrawer(navigation_view)
        }
        menu_right.setOnClickListener { setting() }
        navigation_view.setNavigationItemSelectedListener(this)
    }

    //侧滑菜单点击事件监听
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_today -> mViewModel.today()
            R.id.menu_yesterday -> mViewModel.yesterday()
            R.id.menu_random -> mViewModel.random()
//            R.id.menu_collect->mViewMode.random()
//            R.id.menu_setting -> setting()
        }
        drawer_layout.closeDrawer(navigation_view)
        return true
    }

    private fun setting() {
        mReadConfigBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.pop_read_config, null, false)
        mReadConfigViewModel = ReadConfigViewModel(mViewModel)
        mReadConfigBinding.read = mReadConfigViewModel
        val pop = PopupWindow(
            mReadConfigBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        pop.setBackgroundDrawable(BitmapDrawable())
        pop.isFocusable = true
        pop.isOutsideTouchable = true
        pop.isTouchable = true
        pop.animationStyle = R.style.pop_anim
        pop.showAtLocation(drawer_layout, Gravity.BOTTOM, 0, 0)
    }
}
