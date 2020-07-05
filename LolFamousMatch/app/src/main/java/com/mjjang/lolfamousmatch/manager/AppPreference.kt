package com.mjjang.lolfamousmatch.manager

import android.content.SharedPreferences
import android.preference.PreferenceManager

object AppPreference {

    fun getInstance(): SharedPreferences {
        return preference
    }

    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.applicationContext())

    private operator fun set(key: String, value: Any?) {
        when (value) {
            is String? -> preference.edit().putString(key, value).apply()
            is Int -> preference.edit().putInt(key, value).apply()
            is Boolean -> preference.edit().putBoolean(key, value).apply()
            is Float -> preference.edit().putFloat(key, value).apply()
            is Long -> preference.edit().putLong(key, value).apply()
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    private inline operator fun <reified T : Any> get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> preference.getString(key, defaultValue as? String) as T?
            Int::class -> preference.getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> preference.getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> preference.getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> preference.getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    const val SELECT = 1
    const val NONE = 0

    fun putTagSelected(id: String?, value: Int) {
        id?.let {
            val key = "tag_$id"
            preference.edit().putInt(key, value).apply()
        }
    }

    fun getTagSelected(id: String?) : Int {
        id?.let {
            val key = "tag_$id"
            return preference.getInt(key, 0)
        }
        return 0
    }

    fun getTagSelectedAll() : List<String> {
        return preference.all.filter {
            it.key.startsWith("tag_")
                    && (it.value as Int) == 1
        }.map { it.key.removePrefix("tag_") }
    }
}