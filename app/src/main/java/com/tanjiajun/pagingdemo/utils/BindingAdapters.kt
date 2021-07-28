package com.tanjiajun.pagingdemo.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

/**
 * Created by TanJiaJun on 7/29/21.
 */
@BindingAdapter("android:src")
fun ImageView.setImageSource(@DrawableRes resId: Int) =
    setImageResource(resId)