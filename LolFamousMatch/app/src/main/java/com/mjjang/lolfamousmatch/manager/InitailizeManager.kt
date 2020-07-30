package com.mjjang.lolfamousmatch.manager

import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.mjjang.lolfamousmatch.firestore.FireStoreProc
import com.mjjang.lolfamousmatch.utilities.VersionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object InitailizeManager {

    fun init(context: Context) {
        // firebase 인증
        //AuthManager.init(this)

        // Admob 초기화
        MobileAds.initialize(context)

        // 버전 체크
        VersionManager.doVersionCheck(context)

        // ROOM 데이터 입력 --- 작업 종료될 때 까지 대기..
        FireStoreProc.getMatchList(AppPreference.getTagSelectedAll())
        FireStoreProc.getFilterAll()

        runBlocking {

            delay(3000)
        }
    }
}