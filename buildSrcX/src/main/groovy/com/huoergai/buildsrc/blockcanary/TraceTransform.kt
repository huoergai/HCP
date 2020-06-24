package com.huoergai.buildsrc.blockcanary

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

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

    private fun traceJarFiles(jarInput: JarInput, op: TransformOutputProvider) {
        if (!jarInput.file.absolutePath.endsWith(".jar")) {
            println(jarInput.file.absolutePath)
            return
        }

        val tmpFile = File(jarInput.file.parent, "${jarInput.file.name}.temp")
            .also {
                it.createNewFile()
            }

        JarFile(jarInput.file).use { jarFile ->
            JarOutputStream(FileOutputStream(tmpFile)).use { jarOutputStream ->
                jarFile.entries().iterator().forEach { jarEntry ->
                    val zipEntry = ZipEntry(jarEntry.name)
                    jarFile.getInputStream(zipEntry).use { inputStream ->
                        jarOutputStream.putNextEntry(zipEntry)
                        if (jarEntry.name.endsWith(".class")) {
                            val cr = ClassReader(IOUtils.toByteArray(inputStream))
                            val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                            cr.accept(ClassTraceVisitor(cw), ClassReader.EXPAND_FRAMES)
                            jarOutputStream.write(cw.toByteArray())
                        } else {
                            jarOutputStream.write(IOUtils.toByteArray(inputStream))
                        }
                    }
                    jarOutputStream.closeEntry()
                }
            }
        }

        val dest = op.getContentLocation(
            jarInput.file.nameWithoutExtension + DigestUtils.md5Hex(jarInput.file.absolutePath),
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )
        FileUtils.copyFile(tmpFile, dest)
    }

    private fun traceDirectoryFiles(directoryInputs: DirectoryInput, op: TransformOutputProvider) {
        directoryInputs.file
            .walkTopDown()
            .filter { it.isFile }
            .forEach { file ->
                FileInputStream(file).use { fis ->
                    val cr = ClassReader(fis)
                    val cw = ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
                    val cv = ClassTraceVisitor(cw)
                    cr.accept(cv, ClassReader.EXPAND_FRAMES)
                    file.writeBytes(cw.toByteArray())
                }
            }
        val dest = op.getContentLocation(
            directoryInputs.name,
            directoryInputs.contentTypes,
            directoryInputs.scopes,
            Format.DIRECTORY
        )
        FileUtils.copyDirectory(directoryInputs.file, dest)
    }

}