package com.project.goms.domain.notification.presentation

import com.project.goms.domain.notification.entity.NotificationType
import com.project.goms.domain.notification.usecase.SendNotificationUseCase
import com.project.goms.domain.notification.usecase.SetDeviceTokenUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/notification")
class NotificationController(
    private val setDeviceTokenUseCase: SetDeviceTokenUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase
) {

    @PostMapping("token/{deviceToken}")
    fun setDeviceToken(@PathVariable deviceToken: String): ResponseEntity<Void> =
        setDeviceTokenUseCase.execute(deviceToken)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @GetMapping("outing/before")
    fun beforeOutingTestApi(): ResponseEntity<Map<String, String>> =
        sendNotificationUseCase.execute(NotificationType.BEFORE_OUTING)
            .run { ResponseEntity.ok(mapOf("message" to "success")) }

    @GetMapping("outing/after")
    fun afterOutingTestApi(): ResponseEntity<Map<String, String>> =
        sendNotificationUseCase.execute(NotificationType.AFTER_OUTING)
            .run { ResponseEntity.ok(mapOf("message" to "success")) }

}