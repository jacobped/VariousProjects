//package testCoroutines
import kotlinx.coroutines.experimental.*
/**
 * Created by jacob on 2017-09-13 (YYYY-MM-DD).
 */

fun main(args : Array<String>) {
    println("Hello, world!")
    println("Start")
//    test1()
    test2()
}

fun test1() {
    // Start a coroutine
    launch(CommonPool) {
        delay(1000)
        println("Hello")


        Thread.sleep(2000) // wait for 2 seconds
        println("Stop")
    }
}

fun test2() :Int {
    val deferred = (1..1_000_000).map { n ->
        async (CommonPool) {
            n
        }
    }
//    val sum = deferred.sumBy { it.await() }
    var sum = 0;
    runBlocking {
        sum = deferred.sumBy { it.await() }
        println("Sum: $sum")
    }
    return sum
}

fun test3() {
    async (CommonPool) {
        val result = test2()
    }
}
