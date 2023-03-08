package com.example.mimedicokotlin.ui.videochat

class JavascriptInterface(private val videochatFragment: VideochatFragment) {
    @android.webkit.JavascriptInterface
    fun onPeerConnected(){
        videochatFragment.onPeerConnected()
    }
}