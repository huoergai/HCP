package com.huoergai.hcp.l28io;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.huoergai.hcp.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import kotlin.jvm.JvmStatic;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;

public class IoTest {
    private static final String FILE_NAME = "test.txt";
    private static final String FILE_NAME_COPY = "test_copy.txt";
    private static final int PORT = 5050;

    /**
     * Java input stream/out stream
     * 基本输入输出
     * 带缓冲的输入输出
     */
    @JvmStatic
    public static void basicIO() {
        // 1. input steam
        File file = new File(FILE_NAME);
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)
        ) {
            StringBuilder content = new StringBuilder();
            String tmp = br.readLine();
            while (tmp != null) {
                content.append(tmp);
                tmp = br.readLine();
            }
            Utils.prt(FILE_NAME + " content: " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. out steam
        File outputFile = new File("java_version.txt");
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))
        ) {
            bw.write("work for learning");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基本输入输出流复制文件
     */
    @JvmStatic
    public static void basicIoCopyFile() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             FileOutputStream fos = new FileOutputStream(FILE_NAME_COPY)
        ) {
            byte[] buff = new byte[1024];
            while (fis.read(buff) != -1) {
                fos.write(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 带缓冲的文件复制
     */
    @JvmStatic
    public static void bufferIoCopy() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME_COPY))
        ) {
            String tmp = br.readLine();
            while (tmp != null) {
                bw.write(tmp);
                // 加上换行, 不然都在一行上
                bw.write("\n");
                tmp = br.readLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JvmStatic
    public static void sockIo() {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept();
             InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))
        ) {
            Utils.prt("server start:" + socket.getLocalAddress().getCanonicalHostName());
            while (true) {
                String tmp = br.readLine();
                if (tmp != null) {
                    Utils.prt("server read:" + tmp);
                    bw.write("Got you: " + tmp);
                    bw.write("\n");
                    bw.flush();
                }
            }
            // 在命令行客户端使用：telnet localhost 5050 ,即可链接通信
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JvmStatic
    public static void nio1() {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r");
             FileChannel fc = raf.getChannel()
        ) {
            ByteBuffer byteBuff = ByteBuffer.allocate(1024);
            fc.read(byteBuff);
            // byteBuff.limit(byteBuff.position());
            // byteBuff.position(0);
            // 以上两句等价与此
            byteBuff.flip();
            CharBuffer charBuff = Charset.defaultCharset().decode(byteBuff);
            Utils.prt(charBuff.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JvmStatic
    public static void nio2() {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()
        ) {
            ssc.bind(new InetSocketAddress(PORT));

            // 1.非阻塞式需加上两行配置
            ssc.configureBlocking(false);
            ssc.register(Selector.open(), SelectionKey.OP_ACCEPT);
            int serCount = 0;
            while (true) {
                SocketChannel sc = ssc.accept();
                if (sc != null) {
                    Utils.prt("server " + serCount++);
                    new Thread() {
                        @Override
                        public void run() {
                            ByteBuffer byteBuff = ByteBuffer.allocate(1024);
                            try {
                                while (sc.read(byteBuff) != -1) {
                                    byteBuff.flip();
                                    sc.write(byteBuff);
                                    byteBuff.flip();
                                    Utils.prt("Server Got:" + Charset.defaultCharset().decode(byteBuff));
                                    byteBuff.clear();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
            // 2.阻塞式
            // SocketChannel sc = ssc.accept();

            // ByteBuffer byteBuff = ByteBuffer.allocate(1024);
            // while (sc.read(byteBuff) != -1) {
            //     byteBuff.flip();
            //     sc.write(byteBuff);
            //     byteBuff.flip();
            //     Utils.prt("Server Got:" + Charset.defaultCharset().decode(byteBuff));
            //     byteBuff.clear();
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JvmStatic
    public static void okio1() {
        File file = new File(FILE_NAME_COPY);
        // try (BufferedSource bs = Okio.buffer(Okio.source(new File("./app/test.txt")))) {
        try (BufferedSource bs = Okio.buffer(Okio.source(new File(FILE_NAME)))) {
            Utils.prt("read=" + bs.readUtf8());

            Buffer buff = new Buffer();
            bs.read(buff, 1024);
            Utils.prt(buff.readUtf8Line());

            buff.inputStream();
            buff.outputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
