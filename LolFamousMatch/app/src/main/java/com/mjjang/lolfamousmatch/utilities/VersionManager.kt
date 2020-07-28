package com.mjjang.lolfamousmatch.utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mjjang.lolfamousmatch.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.regex.Pattern

object VersionManager {

    fun doVersionCheck(context : Context) {

        GlobalScope.launch(Dispatchers.Main) {
            val marketVersion = withContext(Dispatchers.Default) {
                getMarketVersion(context)
            }

            val deviceVerison = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            if (removeLastVersionCode(marketVersion) > removeLastVersionCode(deviceVerison)) {
                showUpdateDialog(context)
            }

            marketVersion.lastIndexOf(".")
        }
    }

    fun removeLastVersionCode(version : String) : String {
        val nLastIndex = version.lastIndexOf(".")
        return version.substring(0, nLastIndex)
    }

    private fun getMarketVersion(context: Context) : String {
        val doc = Jsoup
            .connect("https://play.google.com/store/apps/details?id=" + context.packageName)
            .get()
        val version = doc.select(".htlgb")

        for (v in version) {
            if (Pattern.matches("^[0-9]{1}.[0-9]{1}.[0-9]{1}$", v.text())) {
                return v.text()
            }
        }

        return ""
    }

    private fun showUpdateDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.update_dialog_title)
            .setMessage(R.string.update_dialog_content)
            .setNeutralButton(R.string.update_dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.update_dialog_accept) { dialog, which ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=" + context.packageName)
                context.startActivity(intent)
                dialog.dismiss()
            }
            .show()
    }
}