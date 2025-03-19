package com.dev.app.routify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RoutifyApplication

fun main(args: Array<String>) {
    /** println("vars >>>>>>>>>>> ")
    System.getenv().forEach { (key, value) ->
        println("$key: $value")
    }**/

    runApplication<RoutifyApplication>(*args)
}
