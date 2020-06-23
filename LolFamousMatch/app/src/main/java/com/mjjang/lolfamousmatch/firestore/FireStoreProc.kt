package com.mjjang.lolfamousmatch.firestore

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.mjjang.lolfamousmatch.R
import com.mjjang.lolfamousmatch.data.AppDatabase
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.manager.App
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object FireStoreProc {
    val db = FirebaseFirestore.getInstance()

    fun getMatchAll() {
        db.collection("Match")
            .get()
            .addOnSuccessListener { result ->
                val dataList : ArrayList<Match> = ArrayList()
                for (document in result) {
                    val data : Match = Match(
                        (document.data.getValue("id") as Long).toInt(),
                        document.data.getValue("name") as String,
                        document.data.getValue("sub_name") as String,
                        (document.data.getValue("year") as Long).toInt(),
                        (document.data.getValue("tag") as List<*>).toString(),
                        document.data.getValue("link_full") as String,
                        document.data.getValue("link_highlight") as String,
                        (document.data.getValue("recommend") as Long).toInt()
                    )
                    dataList.add(data)
                }
                GlobalScope.launch {
                    val database = AppDatabase.getInstance(App.applicationContext())
                    database.matchDao().insertAll(dataList)
                }
                Toast.makeText(App.applicationContext(), R.string.db_response_success, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
            }
    }
}