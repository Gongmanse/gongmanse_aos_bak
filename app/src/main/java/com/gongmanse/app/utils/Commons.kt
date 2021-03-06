package com.gongmanse.app.utils

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.gongmanse.app.R
import com.gongmanse.app.feature.main.counsel.CounselFragment
import com.gongmanse.app.feature.main.home.HomeFragment
import com.gongmanse.app.feature.main.progress.ProgressFragment
import com.gongmanse.app.feature.main.teacher.TeacherFragment
import com.gongmanse.app.feature.sheet.SelectionSheetUnits
import com.gongmanse.app.utils.listeners.OnBottomSheetToUnitListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import org.jetbrains.anko.alert

class Commons {
    companion object {

        private val TAG = Commons::class.java.simpleName

        // Save Token
        fun saveToken(token: String) {
            Log.d(TAG, "saveToken => $token")
            Preferences.token = token
        }

        // Check Permission: 임시 주석
        fun checkPermission(context: Context, permissionListener: PermissionListener) {
            Log.e(TAG, "checkPermission")
            TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(R.string.content_permission_request) // 권한 요청 이유
                .setRationaleConfirmText(R.string.content_button_confirm) // 확인 버튼
                .setDeniedMessage(R.string.content_permission_request_settings) // 거부했을 때 보여지는 메시지
                .setDeniedCloseButtonText(R.string.content_alert_negative_of_mobile_data)
                .setGotoSettingButton(true)
                .setPermissions(Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE)
                .check()
        }

        // Check Mobile Data: 임시 주석
        fun checkMobileData(context: Context) {
            context.apply {
                context.apply {
                    alert(
                        title = null,
                        message = getString(R.string.content_using_mobile_data_when_play_the_video)
                    ) {
                        positiveButton(getString(R.string.content_alert_positive_of_mobile_data)) {
                            it.dismiss()
                            Preferences.mobileData = true
                        }
                        negativeButton(getString(R.string.content_alert_negative_of_mobile_data)) {
                            it.dismiss()
                            Preferences.mobileData = false
                        }
                    }.show()
                }
            }
        }

        // Check Permission of camera
        fun checkCameraPermission(context: Context, permissionListener: PermissionListener) {
            TedPermission.with(context)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(R.string.content_permission_camera)
                .setDeniedMessage(R.string.content_permission_request)
                .setPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .check()

        }

        fun findFragmentAppTitle(context: Context, fragmentSimpleName: String): String? {
            return when (fragmentSimpleName) {
                HomeFragment::class.java.simpleName -> null
                ProgressFragment::class.java.simpleName -> context.resources.getString(R.string.bottom_navigation_progress)
//                SearchFragment::class.java.simpleName -> context.resources.getString(R.string.bottom_navigation_search)
                CounselFragment::class.java.simpleName -> context.resources.getString(R.string.bottom_navigation_chat)
                TeacherFragment::class.java.simpleName -> context.resources.getString(R.string.bottom_navigation_teacher)
                else -> null
            }
        }

        // BottomSheet Type, Add an item according to type
        fun checkSelectionSheetType(type: Int): Int? {
            return when (type) {
                Constants.SelectValue.SORT_ITEM_TYPE_GRADE           -> R.array.itemGrade
                Constants.SelectValue.SORT_ITEM_TYPE_GRADE_SEARCH    -> R.array.itemGradeSearch
                Constants.SelectValue.SORT_ITEM_TYPE_HOME            -> R.array.itemHome
                Constants.SelectValue.SORT_ITEM_TYPE_SPINNER         -> R.array.itemSpinner
                Constants.SelectValue.SORT_ITEM_TYPE_SPINNER_VIDEO   -> R.array.itemSpinnerVideo
                Constants.SelectValue.SORT_ITEM_TYPE_SPINNER_COUNSEL -> R.array.itemSpinnerCounsel
                else -> null
            }
        }

        fun checkGrade(grade: String?): String? {
            Constants.GradeType.apply {
                return when (grade?.get(0)) {
                    All_VIEW        -> All_GRADE
                    ELEMENTARY_VIEW -> ELEMENTARY
                    MIDDLE_VIEW     -> MIDDLE
                    HIGH_VIEW       -> HIGH
                    else            -> null
                }
            }
        }

        fun checkGradeNum(grade: String?) : Int? {
            val regexInt = "[^0-9]".toRegex()
            Constants.GradeType.apply {
                return when (grade?.replace(regexInt, "")) {
                    VALUE_GRADE_STRING_NUM_FIRST  -> VALUE_GRADE_STRING_NUM_FIRST.toInt()
                    VALUE_GRADE_STRING_NUM_SECOND -> VALUE_GRADE_STRING_NUM_SECOND.toInt()
                    VALUE_GRADE_STRING_NUM_THIRD  -> VALUE_GRADE_STRING_NUM_THIRD.toInt()
                    VALUE_GRADE_STRING_NUM_FOURTH -> VALUE_GRADE_STRING_NUM_FOURTH.toInt()
                    VALUE_GRADE_STRING_NUM_FIFTH  -> VALUE_GRADE_STRING_NUM_FIFTH.toInt()
                    VALUE_GRADE_STRING_NUM_SIXTH  -> VALUE_GRADE_STRING_NUM_SIXTH.toInt()
                    else                          -> Constants.Init.INIT_INT
                }
            }

        }

    }

    }


