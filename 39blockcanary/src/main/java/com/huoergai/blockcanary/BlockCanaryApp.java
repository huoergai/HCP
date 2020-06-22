package com.huoergai.blockcanary;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * 当前版本的 block canary 如果运行在 28/29 等高版本上会报一系列的权限拒绝错误
 * java.io.FileNotFoundException: /proc/stat: open failed: EACCES (Permission denied)
 */
public class BlockCanaryApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        BlockCanary.install(this, new BlockCanaryContext()).start();

        // 方式1 BlockCanary: 捕获超出阈值的主线程任务日志,
        // - 从主线程唯一的 mainLooper 中插入 mlogging(printer) 作为切入点;
        // - 利用 looper 中执行 target（handler）的分发任务前后会分别判断调用 mlogging 对象的 println 方法;
        // - 再根据前后两次执行的时间差结合前期设置的阈值既可判断是否卡顿;
        // - 剩余重点就是在传入的 printer 对象的 println 方法中如何定位和获取日志信息。
        // - 在 println 第一次调用时,会延迟执行一个任务,如果在阈值时间内再此调用 println, 则会移除该任务,反之则执行任务
        //   获取主线程堆栈信息,输出日志等.
        //   缺点是基本够用但不够精确,无法确定嵌套调用方法中各自的执行耗时；

        // logging.println(">>>>> Dispatching to " + msg.target + " " + msg.callback + ": " + msg.what);
        // msg.target.dispatchMessage(msg);
        // logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);

        // 方式2 Choreographer: 基于“Called when a new display frame is being rendered.”, 可判断两次绘制之间是否有卡顿.
        // Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
        //     @Override
        //     public void doFrame(long frameTimeNanos) {
        //         Log.d("doFrame", "nanoTime=" + frameTimeNanos);
        //         Choreographer.getInstance().postFrameCallback(this);
        //     }
        // });

        // 方式3 hugo-plugin: 可以获取方法的耗时,依赖于 aspectJ.
        // - 按文档引入后,使用相应注解(@DebugLog)标记要测试的类或者方法即可.
        // - 局限性：但代码侵入性太强,不够灵活; 对第三方库和 framework 的方法无法统计; 仅适用于开发调试时在 log 中查看运行时间.

        // 方式4 TraceView:
        // - 开销大,结果可能不准确.
        // Debug.startMethodTracing("trace log file path");
        // codes ...
        // Debug.stopMethodTracing();

        // 方式5 systrace:
        // - 在关键点插入观测点,也是 framework 层的用法.
        // - 无性能负担,但在应用层使用侵入性太强,需手动添加观测点.
        // 使用：
        // 1.在代码中添加观测点
        // Trace.beginSection("sectionName");
        // code ...
        // Trace.endSection();

        // 2. 确保 SDK-adb 和 Python 可用
        // 3. 命令行进入 SDK/platform-tools/systrace/ 目录, 执行 “python systrace.py -a packageName view am... -o outputFilename” 开始监测.
        // 4. 运行应用,开始日志收集（使用 “./systrace.py -l” 命令查看可选的监测项目）.
        // 5. 命令行 “enter” 停止监测,得到 trace 日志文件, 使用浏览器查看.

        // 方式6 当 API 28以上时,可使用系统自带的 “System Tracing” 程序.
        // - 在设置里面搜索使用系统自带的 “System Tracing”
        // - 打开 “System Tracing” 点击开始监测, 进入要测试的 app 进行相应操作后, 在“System Tracing”中停止.
        // - 使用命令导出 trace 文件：adb pull data/local/traces
        // - 在 Perfetto 网址中查看获得的日志文件, ad 键左右移动,sw缩放, "Processes" 选项可过滤.
        // - 参考 Android developer 中的 Best practices-Performance-System tracing.

        // 字节码 工具库 aspectJ aspectJX Javassist ASM

    }
}
