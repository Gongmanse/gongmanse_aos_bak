package com.gongmanse.app.feature.Intro

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gongmanse.app.R
import com.gongmanse.app.utils.Commons
import com.gongmanse.app.utils.Preferences
import com.gun0912.tedpermission.PermissionListener
import kotlinx.android.synthetic.main.activity_intro.*
import kotlin.system.exitProcess

class IntroActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val TAG = IntroActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate Intro")
        setContentView(R.layout.activity_intro)
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        exitProcess(0)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_next -> {
                Preferences.first = false
                finish()
            }
        }
    }

    private var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            if (Preferences.first) Commons.checkMobileData(this@IntroActivity)
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Log.d(TAG, "${deniedPermissions.toString()} ${R.string.content_toast_permission_denied}")
        }
    }


}