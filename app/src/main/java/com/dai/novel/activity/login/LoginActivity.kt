package com.dai.novel.activity.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.dai.novel.R
import com.dai.novel.activity.BaseActivity
import com.dai.novel.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by dai on 2018/1/24.
 */

class LoginActivity : BaseActivity() {

    private val PERMISSIONS = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private val REQUEST_CODE: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE)
        }
        setOnClickListener()
    }

    //设置点击事件
    fun setOnClickListener() {
        register.setOnClickListener {
            val intent = Intent().setClass(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        login.setOnClickListener {
            val intent = Intent().setClass(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}

