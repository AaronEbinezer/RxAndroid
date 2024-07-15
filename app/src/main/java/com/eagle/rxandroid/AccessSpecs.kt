package com.eagle.rxandroid

open internal class AccessSpecs {
    private var a = 0
    protected open var b = 0
    internal open var c = 0

    protected fun fnMethod() {
        println()
    }

    class ASub: AccessSpecs() {
        fun method(ab: AccessSpecs) {
            b = 0 // possible
            ab.b = 0 // possible
        }
    }
}

private class ASub: AccessSpecs() {
    override var b: Int = 0
    fun method(a: AccessSpecs) {
        b = 0
        a.c = 0
    }
}