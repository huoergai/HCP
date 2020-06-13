package com.huoergai.http

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

/**
 * 07-08 HTTP Retrofit OkHttp
 */
class MainActivity : AppCompatActivity() {

    private var wsSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*    val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/users/huoergai/repos")
                .build()
            val apiService = retrofit.create(ApiService::class.java)
            val userCall = apiService.getUser("id")
            val response = userCall.execute()*/


        val iv = findViewById<ImageView>(R.id.iv)
        /*Glide.with(this)
            .load("")
            .into(iv)*/

        wsTest()
    }

    /**
     * WebSocket Test
     */
    private fun wsTest() {
        val client = OkHttpClient.Builder().build()
        val req = Request.Builder()
            .url("ws://echo.websocket.org")
            .build()
        val wsListener = WsListener()
        val tv = findViewById<MaterialTextView>(R.id.tv)

        val btnConnect = findViewById<Button>(R.id.btn_connect)
        val btnSend = findViewById<Button>(R.id.btn_send)
        val btnDisconnect = findViewById<Button>(R.id.btn_disconnect)


        btnConnect.setOnClickListener {
            client.newWebSocket(req, wsListener)
        }
        btnSend.setOnClickListener {
            wsSocket?.send("It's: ${System.currentTimeMillis()}")
        }
        btnDisconnect.setOnClickListener {
            val graceFul = wsSocket?.close(1000, "mission completed")
            tv.text = "${tv.text} \n graceFul:$graceFul"
        }
    }

    fun onClick(view: View) {
        val cp = CertificatePinner.Builder()
            .add("api.github.com", "sha256/ORH27mxcLwxnNpR7e0i6pdDPWLXdpeWgr5bEfFVasW8=")
            .build()

        val client = OkHttpClient.Builder()
            .certificatePinner(cp)
            .build()


        val request = Request.Builder()
            .url("https://api.github.com/users/huoergai/repos")
            .build()
        client.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("error msg = ")
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("response status code:  ${response.code}")
                }
            })
    }

    inner class WsListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            // super.onOpen(webSocket, response)
            wsSocket = webSocket
            wsSocket?.send("hello, now we can talk...")
            Log.d("WSS", "onOpen")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            // super.onMessage(webSocket, text)
            runOnUiThread {
                tv.text = "${tv.text} \n receive: $text"
            }
            Log.d("WSS", "onMessage: $text")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            // super.onClosing(webSocket, code, reason)
            Log.d("WSS", "onClosing: $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            // super.onFailure(webSocket, t, response)
            Log.d("WSS", "onFailure: ${t.message}")
        }
    }

}
