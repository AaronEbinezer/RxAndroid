package com.eagle.rxandroid.vmodel.model.maps

class Order(val lines: List<OrderLine>)
class OrderLine(var name: String, val price: Int)