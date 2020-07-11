package com.mjjang.lolfamousmatch.firestore

import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mjjang.lolfamousmatch.R
import com.mjjang.lolfamousmatch.data.AppDatabase
import com.mjjang.lolfamousmatch.data.Match
import com.mjjang.lolfamousmatch.data.MatchType
import com.mjjang.lolfamousmatch.manager.App
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object FireStoreProc {
    val db = FirebaseFirestore.getInstance()

    fun getMatchList(tagList: List<String>) {
        if (tagList.isNotEmpty()) {
            getMatchByFilter(tagList)
        } else {
            getMatchAll()
        }
    }

    private fun getMatchAll() {
        db.collection("Match")
            .get()
            .addOnSuccessListener { result ->
                val dataList : ArrayList<Match> = ArrayList()
                for (document in result) {
                    val data : Match = Match(
                        document.id,
                        document.data.getValue("name") as String,
                        document.data.getValue("sub_name") as String,
                        (document.data.getValue("year") as Long).toInt(),
                        getArrayToString((document.data.getValue("tag") as List<String>)),
                        document.data.getValue("link_full") as String,
                        document.data.getValue("link_highlight") as String,
                        (document.data.getValue("recommend") as Long).toInt()
                    )
                    dataList.add(data)
                }
                GlobalScope.launch {
                    val database = AppDatabase.getInstance(App.applicationContext())
                    database.matchDao().deleteAndInsert(dataList)
                }
                Toast.makeText(App.applicationContext(), R.string.db_response_success, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
            }
    }

    private fun getMatchByFilter(tagList: List<String>) {
        db.collection("Match")
            .whereArrayContainsAny("tag", tagList)
            .get()
            .addOnSuccessListener { result ->
                val dataList : ArrayList<Match> = ArrayList()
                for (document in result) {
                    val data : Match = Match(
                        document.id,
                        document.data.getValue("name") as String,
                        document.data.getValue("sub_name") as String,
                        (document.data.getValue("year") as Long).toInt(),
                        getArrayToString((document.data.getValue("tag") as List<String>)),
                        document.data.getValue("link_full") as String,
                        document.data.getValue("link_highlight") as String,
                        (document.data.getValue("recommend") as Long).toInt()
                    )
                    dataList.add(data)
                }
                GlobalScope.launch {
                    val database = AppDatabase.getInstance(App.applicationContext())
                    database.matchDao().deleteAndInsert(dataList)
                }
                Toast.makeText(App.applicationContext(), R.string.db_response_success, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
            }
    }

    fun getFilterAll() {
        db.collection("filter")
            .get()
            .addOnSuccessListener { result ->
                val dataList : ArrayList<MatchType> = ArrayList()
                for (document in result) {
                    val data : MatchType = MatchType(
                        document.data.getValue("name") as String,
                        document.data.getValue("category") as String,
                        (document.data.getValue("select_count") as Long).toInt()
                    )
                    dataList.add(data)
                }
                GlobalScope.launch {
                    val database = AppDatabase.getInstance(App.applicationContext())
                    database.matchTypeDao().deleteAndInsert(dataList)
                }
                Toast.makeText(App.applicationContext(), R.string.db_response_success, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getArrayToString(list: List<String>) : String {
        val iterator = list.listIterator()
        var tagString : String = String()
        while(iterator.hasNext()) {
            val string = iterator.next()
            if (!string.isNullOrBlank()) {
                tagString += "$string,"
            }
        }
        return tagString.dropLastWhile { c -> c == ',' }
    }

    fun insertComment(list: Map<String,Any>) {

        db.collection("Comment")
            .add(list)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun insertComment(matchId: String, writer: String, content: String) {
        val data = hashMapOf(
            "match_id" to matchId,
            "writer" to writer,
            "create_date" to FieldValue.serverTimestamp(),
            "content" to content
        )

        insertComment(data)
    }
}