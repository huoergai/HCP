package com.huoergai.buildsrc.blockcanary

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class MethodTraceVisitor(api: Int, mv: MethodVisitor, access: Int, name: String?, descriptor: String?) :
    AdviceAdapter(api, mv, access, name, descriptor) {

    lateinit var className: String

    override fun onMethodEnter() {
        super.onMethodEnter()

        mv.visitLdcInsn("${className}/${this.name}")
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/os/Trace",
            "beginSection",
            "(Ljava/lang/String;)V",
            false
        )
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)

        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/os/Trace",
            "endSection",
            "()V",
            false
        )
    }
}