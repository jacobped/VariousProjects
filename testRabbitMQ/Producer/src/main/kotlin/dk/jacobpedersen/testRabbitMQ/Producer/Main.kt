/**
 * Created by jacob on 2017-09-25 (YYYY-MM-DD).
 */
package dk.jacobpedersen.testRabbitMQ.Producer
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel

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
    val message = "Hello World!"
    for (i in 1..1000) {
        channel.basicPublish("", QUEUE_NAME, null, message.toByteArray(charset("UTF-8")))
        println(" [x] Sent '$message'")
    }
    channel.close()
    connection.close()
}

class send() {
    val QueueName : String = "hello"


}