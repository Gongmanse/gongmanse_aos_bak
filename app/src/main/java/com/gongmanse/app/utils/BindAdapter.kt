package com.gongmanse.app.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.gongmanse.app.R
import com.gongmanse.app.data.model.progress.ProgressBody
import com.gongmanse.app.data.model.video.VideoBody
import com.gongmanse.app.feature.main.counsel.CounselListAdapter
import com.gongmanse.app.feature.main.progress.adapter.ProgressRVAdapter

// URL Image Binding
@BindingAdapter("bindProfileURL")
fun bindViewProfileURL(view: ImageView, value: String?) {
    if (value == null) {
        view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_profile_gray))
    } else {
        val requestOptions = RequestOptions().apply {
            override(300, 300)
            diskCacheStrategy(DiskCacheStrategy.NONE)
            skipMemoryCache(false)
            placeholder(R.drawable.ic_profile_gray)
            error(R.drawable.ic_profile_gray)
            centerCrop()
            circleCrop()
            signature(ObjectKey(System.currentTimeMillis()))
        }
        Glide.with(view.context)
            .load(value)
            .apply(requestOptions)
            .into(view)
    }
}

@BindingAdapter("bindURLImage")
fun bindViewURLImage(view: ImageView, value: String?) {
    if (value != null) {
        if (value.endsWith(".mp4")) {
            val options = RequestOptions()
            options.isMemoryCacheable
            Glide.with(view.context)
                .setDefaultRequestOptions(options)
                .load("$value")
                .thumbnail(0.1f)
                .error(R.drawable.ic_alert)
                .into(view)
        } else {
            Glide.with(view.context)
                .load("$value")
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_alert)
                .into(view)
        }
    } else view.setImageResource(R.drawable.ic_alert)
}

@BindingAdapter("bindGradeTextOfProgress")
fun bindViewGradeTextOfProgress(view: TextView, value: String?) {
    if (value.isNullOrEmpty()) Log.e("bindView", " progress grade value is null ")
    else {
        value.let {
            view.text = when (value[0]) {
                Constants.Progress.VALUE_ELEMENTARY_VIEW -> Constants.Progress.VALUE_ELEMENTARY_VIEW.toString()
                Constants.Progress.VALUE_MIDDLE_VIEW -> Constants.Progress.VALUE_MIDDLE_VIEW.toString()
                Constants.Progress.VALUE_HIGH_VIEW -> Constants.Progress.VALUE_ELEMENTARY_VIEW.toString()
                else -> null
            }
        }
    }
}

@BindingAdapter("bindUnitText")
fun bindViewUnitText(view: TextView, value: String?) {
    value?.let {
//        if (it == Constants.CONTENT_VALUE_ACTIVE_UNIT_TERM) view.text = value
//        else view.text = if (value == "1") "ⅰ" else "ⅱ"
    }
}

@BindingAdapter("bindUnitColor")
fun bindViewUnitColor(view: CardView, value: String?) {
    value?.let {
//        if (it == Constants.CONTENT_VALUE_ACTIVE_UNIT_TERM) view.setCardBackgroundColor(
//            ContextCompat.getColor(view.context, R.color.term_color))
//        else view.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.term_other_than_color))
    }
}

// Search Counsel List Data Binding
@BindingAdapter("bindSearchCounselData")
fun bindViewCounselListData(view: RecyclerView, values: ObservableArrayList<VideoBody>?) {
    Log.d("bindSearchCounselData", " In")
    val mAdapter = view.adapter
    if (mAdapter != null) {
        Log.d("bindSearchCounselData", "=> $mAdapter")
        values?.let { (mAdapter as CounselListAdapter).addItems(it) }
    }
}

// Progress List Binding
@BindingAdapter("bindProgress")
fun bindViewProgress(view: RecyclerView, value: ArrayList<ProgressBody>?) {
    Log.d("bindProgress", " value => $value")
    val mAdapter = view.adapter
    if (mAdapter != null) {
        Log.d("bindProgressList", "=> $mAdapter")
        value?.let { (mAdapter as ProgressRVAdapter).addItems(it) }
    }
}