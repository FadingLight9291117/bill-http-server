package com.fadinglight.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import com.fadinglight.models.BillItem
import com.fadinglight.models.bills
import io.ktor.http.*
import io.ktor.server.request.*

fun Route.billRoutes() {
    get("/") {
        call.respondText("Hello World!", status = HttpStatusCode.OK)
    }
    post("/bill") {
        val billItem = call.receive<BillItem>()
        call.application.environment.log.info(billItem.toString())
        bills.add(billItem)

        call.respond(HttpStatusCode.OK)
    }
    get("/bill/{id}") {
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Missing or malformed id",
            status = HttpStatusCode.BadRequest
        )
        val billItem = bills.find { it.id == id } ?: return@get call.respondText(
            "No customer with id $id",
            status = HttpStatusCode.NotFound
        )
        call.respond(billItem)
    }
    get("/bills") {
        call.respond(bills)
    }
}

fun Application.registerBillRoutes() {
    routing {
        billRoutes()
    }
}