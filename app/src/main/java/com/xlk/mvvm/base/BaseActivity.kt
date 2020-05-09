package com.xlk.mvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by xlk on 2020/4/29.
 *
 */
abstract class BaseActivity<VM : BaseViewModel, M : BaseModel> : AppCompatActivity() {
    lateinit var viewModel: VM
    lateinit var model: M
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}