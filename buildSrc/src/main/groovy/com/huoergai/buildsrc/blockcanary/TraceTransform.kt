package com.huoergai.buildsrc.blockcanary

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager

class TraceTransform : Transform() {
    override fun getName(): String {
        return "traceTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(ti: TransformInvocation) {

        ti.inputs.forEach {

            it.jarInputs.forEach { jarInput ->
                traceJarFiles(jarInput, ti.outputProvider)
            }

            it.directoryInputs.forEach { directoryInput ->
                traceDirectoryFiles(directoryInput, ti.outputProvider)
            }
        }
    }

    private fun traceJarFiles(transformInput: JarInput, op: TransformOutputProvider) {

    }

    private fun traceDirectoryFiles(directoryInputs: DirectoryInput, op: TransformOutputProvider) {

    }
}