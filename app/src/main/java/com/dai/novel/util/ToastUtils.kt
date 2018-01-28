package com.dai.novel.util

import android.app.Activity
import android.widget.Toast

/**
 * Created by dai on 2018/1/24.
 */
fun Activity.toast(string: String) {
    this.runOnUiThread {
        Toast.makeText(this.applicationContext, string, Toast.LENGTH_SHORT).show();
    }
}