package com.example.badfinalkotlin

import android.content.Intent

interface onIntentReceived {
    fun onIntent(intent: Intent, resultCode: Int)
}