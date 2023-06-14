package com.nicolascastilla.challenge6.utils

import android.content.Context
import android.media.RingtoneManager
import com.nicolascastilla.data.local.interfaces_core.SystemSoundUsage
import javax.inject.Inject

class SystemSoundImpl @Inject constructor(
    private val context: Context,
): SystemSoundUsage {

    override fun reproduceSound() {
        val defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(context, defaultRingtoneUri)
        ringtone?.play()
        //ringtone?.stop()
    }
}