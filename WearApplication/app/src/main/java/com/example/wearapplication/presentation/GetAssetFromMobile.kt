package com.example.wearapplication.presentation

import android.util.Log
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.WearableListenerService

class GetAssetFromMobile: WearableListenerService() {
    companion object {
        private const val TAG = "GetAssetFromMobile"
    }
    override fun onDataChanged(p0: DataEventBuffer) {
        Log.d(TAG, "onDataChanged: is called...")
    }
}