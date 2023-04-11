package com.project.goms.global.gauth.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("gauth")
class GAuthProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String
)