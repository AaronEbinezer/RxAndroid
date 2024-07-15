package com.eagle.rxandroid

internal class SieveOfEratosthenes {
    fun sieveOfEratosthenes(n: Int) {
        // Create a boolean array "prime[0..n]" and
        // initialize all entries it as true. A value in
        // prime[i] will finally be false if i is Not a
        // prime, else true.
        val prime = BooleanArray(n + 1)
        for (i in 0..n) prime[i] = true
        var p = 2
        while (p * p <= n) {

            // If prime[p] is not changed, then it is a
            // prime
            if (prime[p]) {
                // Update all multiples of p greater than or
                // equal to the square of it numbers which
                // are multiple of p and are less than p^2
                // are already been marked.
                var i = p * p
                while (i <= n) {
                    prime[i] = false
                    i += p
                }
            }
            p++
        }

        // Print all prime numbers
        for (i in 2..n) {
            if (prime[i]) print("$i ")
        }
    }
}

fun main() {
    val n = 30
    print("Following are the prime numbers ")
    println("smaller than or equal to $n")
    val g = SieveOfEratosthenes()
    g.sieveOfEratosthenes(n)
}
