package com.envious.imagemachine.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.envious.imagemachine.R

object BindingConverters {
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_not_found)
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }
}
