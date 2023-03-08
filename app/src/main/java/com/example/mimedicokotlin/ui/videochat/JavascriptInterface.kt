package com.example.mimedicokotlin.ui.videochat

class JavascriptInterface(private val videochat: Videochat) {
    @android.webkit.JavascriptInterface
    fun onPeerConnected(){
        videochat.onPeerConnected()
    }
}