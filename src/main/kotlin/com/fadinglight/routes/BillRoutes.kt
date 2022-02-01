package com.fadinglight.routes

import com.fadinglight.database.Mongo
import com.fadinglight.models.Bill
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import com.fadinglight.models.BillItem
import io.ktor.http.*
import io.ktor.server.request.*
import org.litote.kmongo.eq


fun Route.billRoutes() {
    val mongo = Mongo()

    get("/") {
        call.respondText("Hello, this is the bill sys server!", status = HttpStatusCode.OK)
    }

    post("/bill/{date}") {
        val date = call.parameters["date"] ?: return@post call.respondText(
            "Bad Request",
            status = HttpStatusCode.BadRequest,
        )
        val billItem = call.receive<BillItem>()
        val mongo = mongo<Bill>()
        val bill = mongo.findOne(Bill::date eq date)
        if (bill != null) {
            bill.contents.add(billItem)
            mongo.updateOneById(bill._id, bill)
        } else {
            val newBill = Bill(date = date, contents = mutableListOf(billItem))
            mongo.insertOne(newBill)
        }

        call.respond(HttpStatusCode.OK)
    }

    delete("/bill/{date}/{billItemId}") {
        val date = call.parameters["date"] ?: return@delete call.respondText(
            "Bad Request, not date",
            status = HttpStatusCode.BadRequest,
        )
        val billItemId = call.parameters["billItemId"] ?: return@delete call.respondText(
            "Bad Request, not id",
            status = HttpStatusCode.BadRequest,
        )
        val mongo = mongo<Bill>()
        val bill = mongo.findOne(Bill::date eq date) ?: return@delete call.respondText(
            "Not Found, bill not found",
            status = HttpStatusCode.NotFound,
        )
        val billId = bill._id
        val billItem = bill.contents.find { it._id.toString() == billItemId } ?: return@delete call.respondText(
            "Not Found, billItem not found",
            status = HttpStatusCode.NotFound,
        )
        billItem.removed = true
        mongo.updateOneById(billId, bill)
        call.respond(HttpStatusCode.OK)
    }

    // 获取指定日期date的账单
    get("/bill/{date}") {
        val date = call.parameters["date"] ?: return@get call.respondText(
            "Bad Request",
            status = HttpStatusCode.BadRequest,
        )
        val bill = mongo<Bill>().findOne(Bill::date eq date) ?: return@get call.respondText(
            "not found the bill",
            status = HttpStatusCode.NotFound,
        )
        call.respond(bill)
    }

    // 获取全部日期的账单
    get("/bills") {
        val bills = mongo<Bill>().find().toList().filter { bill ->
            bill.contents.any { !it.removed }
        }
        call.respond(bills)

    }
}

fun Application.registerBillRoutes() {
    routing {
        billRoutes()
    }
}