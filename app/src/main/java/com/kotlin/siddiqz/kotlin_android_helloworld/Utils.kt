package com.kotlin.siddiqz.kotlin_android_helloworld

class Utils {




    private fun arraySnippets() {

        val myArray = arrayListOf(1, 1.33, "String")
        println(myArray.toString())

        myArray[1] = 1.00

        println("The length of myArray is ${myArray.size}")
        println("Is String in myArray? ${myArray.contains("String")} And is String 2 there? ${myArray.contains("String2")}")
        println("Where is String in myArray? ${myArray.indexOf("String")} and String2? ${myArray.indexOf("String2")} ")

        val sqArray = Array(5, { i-> i * i } )
        println(sqArray[1].toString())

        val intArray : Array<Int> = arrayOf(1,2,3,4,5)
        println(intArray[3].toString())
    }

    private fun rangeSnippets() {
        val oneTo10 = 1..10
        val alpha = "A".."Z"
        println("R in alpha :  ${ "R" in alpha }")

        val tenTo1 = 10.downTo(1)
        val twoTo20 = 2.rangeTo(20)

        val rng3 = oneTo10.step(3)

        for(x in rng3) println("rng3 : $x")
        for(x in tenTo1.reversed()) println("Reverse : $x")
    }

    private fun conditionSnippets() {
        val age = 8

        if(age < 5) {
            println("Go to Preschool")
        } else if (age == 5) {
            println("Go to Kindergarten")
        } else if ( (age > 5) && (age <= 17) ) {
            val grade = age - 5
            println( "Go to Grade $grade" )
        } else {
            println("Go to College")
        }

        when(age) {
            0,1,2,3,4 -> println("Go to Preschool")
            5 -> println("Go to Kindergarten")
            in 6..17 -> {
                val grade = age - 5
            }
        }
    }

    private fun loopingSnippets() {
        for(x in 1..10) {
            println("Loop: $x")
        }

        val arr: Array<Int> = arrayOf(1,2,3,4,5)
        for(index in arr.indices) {
            println("Index : $index, Value : ${arr[index]}")
        }

        for( (index, value) in arr.withIndex() ) {
            println("Index: $index Value: $value")
        }
    }

    private fun functionSnippets() {
        println(addNumbers(1,2).toString())
        println(subtractNumbers(2,1).toString())
        println(subtractNumbers(2).toString())
        println(subtractNumbers(num2 = 3, num1 = 4).toString())
        sayHello("")
        val (two, three) = nextTwo(1)
        println( "1, $two, $three" )
    }

    private fun addNumbers(num1 : Int, num2 : Int) : Int = num1 + num2
    private fun subtractNumbers(num1 : Int = 1, num2 : Int = 1) : Int = num1 - num2
    private fun sayHello(message: String = "") : Unit = println(message)
    private fun nextTwo(num1: Int) : Pair<Int, Int> {
        return Pair(num1+1, num1+2)
    }
}