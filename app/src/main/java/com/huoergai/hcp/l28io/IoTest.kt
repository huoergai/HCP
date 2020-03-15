package com.huoergai.hcp.l28io

import java.io.*
import java.net.ServerSocket
import java.text.SimpleDateFormat
import java.util.*

private const val FILE_NAME = "test.txt"
private const val FILE_NAME_COPY = "test_copy.txt"

fun main() {
    // io1()
    // io2()
    // io3()
    // io4()
    // io5()
    io6()
}

/**
 * ServerSocket 输入、输出
 */
fun io6() {
    val ss = ServerSocket(8080)
    val socket = ss.accept()
    val sis = socket.getInputStream()
    val br = BufferedReader(InputStreamReader(sis))
    val os = socket.getOutputStream()
    val bw = BufferedWriter(OutputStreamWriter(os))
    var tmp = ""
    while (tmp != "-1") {
        tmp = br.readLine()
        bw.write("echo: $tmp\n")
        bw.flush()
    }
}

/**
 * 基本输入输出流复制文件
 */
fun io5() {
    val fis = FileInputStream(FILE_NAME)
    val fos = FileOutputStream(FILE_NAME_COPY)
    val ba = ByteArray(1024)
    var len = fis.read(ba)
    while (len != -1) {
        fos.write(ba)
        len = fis.read(ba)
    }
}

/**
 * 带缓冲的输出流：写
 */
fun io4() {
    val fos = FileOutputStream(FILE_NAME_COPY)
    val osw = OutputStreamWriter(fos)
    val bw = BufferedWriter(osw)
    bw.write("io4 It's ${SimpleDateFormat("HH:ss:mm", Locale.CHINA).format(Date())}")
    bw.flush()
}


/**
 * 带缓冲的输入流：读
 */
fun io3() {
    val fis = FileInputStream(FILE_NAME)
    // An InputStreamReader is a bridge from byte streams to character streams
    val isr = InputStreamReader(fis)
    // default buffer size 8k
    val br = BufferedReader(isr)
    var l: String? = br.readLine()
    while (l != null) {
        println(l)
        l = br.readLine()
    }
}

/**
 * 基本输出流：写
 */
fun io2() {
    val file = File(FILE_NAME_COPY)
    if (!file.exists()) {
        file.createNewFile()
    }
    val fos = FileOutputStream(file)
    val osw = OutputStreamWriter(fos)
    osw.write("io2 It's ${SimpleDateFormat("HH:ss:mm", Locale.CHINA).format(Date())}")
    osw.flush()
}

/**
 * 基本输入流：读
 */
fun io1() {
    val fis = FileInputStream(FILE_NAME)
    val isr = InputStreamReader(fis)
    val str = isr.readText()
    println("file content=$str")
}
