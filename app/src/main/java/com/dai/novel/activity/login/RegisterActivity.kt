package com.dai.novel.activity.login

//import com.dai.novel.util.toast
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.dai.novel.R
import com.dai.novel.activity.BaseActivity
import kotlinx.android.synthetic.main.actvity_register.*
import org.jetbrains.anko.toast

/**
 * Created by dai on 2018/1/24.
 */
class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_register)
        registerHandler()
    }

    //注册账号
    fun registerHandler() {
        val userValue = username.text.toString();
        val firstValue = firstInput.text.toString();
        val secondValue = secondInput.text.toString();
        register.setOnClickListener {
            if (TextUtils.isEmpty(userValue) || TextUtils.isEmpty(firstValue) || TextUtils.isEmpty(secondValue)) {
                toast("账号或密码不能为空")
                return@setOnClickListener
            }

            if (firstValue == secondValue) {
                toast("密码不同，请重新输入")
                return@setOnClickListener
            }

            postData(userValue, secondValue)
        }

        existingAccount.setOnClickListener {
            startActivity(Intent().setClass(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun postData(account: String, password: String) {

    }
}