package com.eagle.rxandroid

import com.google.gson.Gson

fun main() {
    val tree = BinarySearch()
    tree.insert(50);
    tree.insert(30);
    tree.insert(20);
    tree.insert(40);
    tree.insert(70);
    tree.insert(60);
    tree.insert(80);
    println("tree ${tree.inOrder()}")
}

class Node(val value: Int) {
    var left: Node? = null
    var right: Node? = null
}

class BinarySearch {
    private var root: Node? = null

    init {
        println("Init")
        root = null
    }

    fun insert(value: Int) {
        root = insertValue(value, root)
    }

    private fun insertValue(value: Int, node: Node?): Node {
        if (node == null) {
            return Node(value)
        }
        if (node.value > value) {
            node.left = insertValue(value, node.left)
        } else {
            node.right = insertValue(value, node.right)
        }
        return node
    }

    fun inOrder() {
        inOrderRec(root)
    }
    /* Let us create following BST
                  50
               /     \
              30      70
             /  \    /  \
           20   40  60   80 */
    private fun inOrderRec(node: Node?) {
        if (node != null) {
            inOrderRec(node.left)
            println("${node.value}")
            inOrderRec(node.right)
        }
    }
}