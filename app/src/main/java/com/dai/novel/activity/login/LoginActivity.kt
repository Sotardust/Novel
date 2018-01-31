package com.dai.novel.activity.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.InputType
import com.dai.novel.R
import com.dai.novel.activity.BaseActivity
import com.dai.novel.activity.MainActivity
import com.dai.novel.adapter.inter.XSingleObserverAdapter
import com.dai.novel.util.OkHttpUtils
import com.dai.novel.util.URLUtil
import com.dai.novel.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


/**
 * Created by dai on 2018/1/24.
 */

class LoginActivity : BaseActivity() {

    private val PERMISSIONS = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private val REQUEST_CODE: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
            handleLoginData()
        }
        forgetPassword.setOnClickListener { toast("功能暂未开发") }
        var flag = true
        eyes.setOnClickListener {
            val resId = if (flag) R.mipmap.ic_open_eyes else R.mipmap.ic_close_eyes
            val type = if (flag) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            password.inputType = type
            eyes.setImageResource(resId)
            flag = !flag
        }
    }

    // 处理登录请求
    fun handleLoginData() {
        val account = username.text.toString()
        val password = password.text.toString()
//        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
//            toast("账号名或密码不能为空")
//            return
//        }
//        postData(account, password)
        postData("xiao", "123456")
    }

    private fun postData(account: String, password: String) {
        val json = JSONObject();
        json.put("account", account)
        json.put("password", password)
        OkHttpUtils().getSinglePostRequest(URLUtil().loginUrl(), json)
                .subscribe(object : XSingleObserverAdapter<String>() {
                    override fun onSuccess(t: String) {
                        try {
                            val json = JSONObject(t)
                            val success = json.getInt("success")
                            if (success == 1) {
                                val intent = Intent().setClass(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else toast(json.getString("error"))
                        } catch (e: Exception) {
                            println("登录异常")
                        }
                    }
                    override fun onError(e: Throwable) {
                        super.onError(e)
                        println("e.toString() = ${e.toString()}")
                    }
                })
    }

}

