package com.pedrofr.wtest.services

import android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DownloadPostcodeCsvReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_DOWNLOAD_COMPLETE) {
            
        }
    }
}