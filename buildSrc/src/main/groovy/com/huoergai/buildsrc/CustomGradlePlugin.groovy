package com.huoergai.buildsrc

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.annotations.NotNull

/**
 * 自定义 gradle 插件
 */
class CustomGradlePlugin implements Plugin<Project> {
    @Override
    void apply(@NotNull Project target) {
        println("custom gradle plugin：")
        def myExtention = target.extensions.create("custom_plugin", CustomPluginExtention.class)
        target.afterEvaluate {
            // 在配置之后执行以下代码
            println("Project name: ${myExtention.name}")
            println("Project age: ${myExtention.age}")
        }

        def customTransform = new CustomTransform()
        def baseExtension = target.extensions.getByType(BaseExtension)
        baseExtension.registerTransform(customTransform)
    }
}
