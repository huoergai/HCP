package com.huoergai.buildsrc.blockcanary

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


class TracePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.getByType(AppExtension::class.java)
            .registerTransform(TraceTransform())
    }
}