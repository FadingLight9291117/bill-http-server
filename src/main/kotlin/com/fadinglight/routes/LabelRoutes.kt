package com.fadinglight.routes

import com.fadinglight.database.Mongo
import com.fadinglight.models.Label
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


suspend fun createLabel(mongo: Mongo): Label {
    val label = Label(mainLabels = mutableListOf(), minorLabels = mutableListOf())
    mongo<Label>().insertOne(label)
    return label
}

fun Route.labelRoutes() {
    val mongo = Mongo()

    get("/label") {
        call.respond(mongo<Label>().findOne() ?: createLabel(mongo))
    }
    post("/label") {
        val labelRec = call.receive<Label>()
        val label = mongo<Label>().findOne() ?: createLabel(mongo)
        label.mainLabels.clear()
        label.mainLabels.addAll(labelRec.mainLabels)
        label.minorLabels.clear()
        label.minorLabels.addAll(labelRec.minorLabels)
        mongo<Label>().updateOneById(label._id, label)
        call.respond(HttpStatusCode.OK)
    }
}

fun Application.registerLabelRoutes() {
    routing {
        labelRoutes()
    }
}