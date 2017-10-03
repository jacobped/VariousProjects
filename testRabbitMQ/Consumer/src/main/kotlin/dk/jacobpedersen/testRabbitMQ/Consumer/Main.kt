/**
 * Created by jacob on 2017-09-25 (YYYY-MM-DD).
 */
package dk.jacobpedersen.testRabbitMQ.Consumer

import java.io.IOException
import com.rabbitmq.client.*;
import kotlinx.coroutines.experimental.delay


private val QUEUE_NAME = "hello"

fun main(args : Array<String>) {
    println("Hello, world!")
    println("Start")
    val factory = ConnectionFactory()
    factory.host = "172.17.0.2"
    factory.port= 5672
    factory.username = "guest"
    factory.password = "guest"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(QUEUE_NAME, false, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val consumer = object : DefaultConsumer(channel) {
        @Throws(IOException::class)
        override fun handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: ByteArray) {
            val message = String(body, charset("UTF-8"))
            println(" [x] Received '$message'")
//            Thread.sleep(500)
        }
    }
    channel.basicConsume(QUEUE_NAME, true, consumer)
}
