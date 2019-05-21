package com.cnpeng.databinding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * 作者：CnPeng
 * 时间：2019-05-17
 * 功用：
 * 其他：
 */
object BindingAdapterUtils {

    @BindingAdapter(value = ["android:typeface"])
    fun setTypeface(textView: View, typeface: Int) {
        if (textView is TextView)
            textView.setTypeface(null, typeface)
    }

    fun TextView.setTypeFace(typeface: Int) {
        setTypeface(null, typeface)
    }
}