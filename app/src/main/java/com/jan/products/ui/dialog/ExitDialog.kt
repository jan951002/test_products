package com.jan.products.ui.dialog

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import com.jan.products.R
import kotlinx.android.synthetic.main.dialog_exit.*

class ExitDialog(private val activity: Activity) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dialog(activity, R.style.ThemeTransparent)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setContentView(R.layout.dialog_exit)
        window?.attributes?.windowAnimations = R.style.DialogAnimation
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)

        btnNoExit.setOnClickListener { dismiss() }
        btnYesExit.setOnClickListener { dismiss(); activity.finish() }
    }

}