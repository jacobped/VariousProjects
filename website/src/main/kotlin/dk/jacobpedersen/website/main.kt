package dk.jacobpedersen.website

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
//import io.ktor.
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("Hello World", ContentType.Text.Html)
        }
        get("/test/") {
            call.respondText("It's a routing test", ContentType.Text.Html)
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("BlogAppKt"), module = Application::module).start()
}
