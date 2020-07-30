package com.mjjang.lolfamousmatch.manager

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.mjjang.lolfamousmatch.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DialogManager {

    fun showLoadingDialog(context: Context) {
        val dummydialog = Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        dummydialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dummydialog.show()

        GlobalScope.launch(Dispatchers.Main) {

            val dialog = Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
            withContext(Dispatchers.Default) {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_loading)
            }
            dialog.show()

            withContext(Dispatchers.Default) {
                InitailizeManager.init(context)
            }

            dummydialog.dismiss()
            dialog.dismiss()
        }
    }
}