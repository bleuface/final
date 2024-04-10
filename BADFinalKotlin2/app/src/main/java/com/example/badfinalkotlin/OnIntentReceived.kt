package com.example.badfinalkotlin

import android.content.Intent

interface OnIntentReceived {
    fun onIntent(intent: Intent, resultCode: Int)
}