package com.eagle.rxandroid.sample

class TestableService {
    fun testFunc() {

    }

    fun getDataFromDb(testParameter: String): String {
        // query database and return matching value
        return ""
//        return doSomethingElse(testParameter)
    }

    fun doSomethingElse(testParameter: String): String {
        return "I don't want to!"
    }
}