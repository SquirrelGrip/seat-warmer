package com.github.squirrelgrip.seat.model

import java.time.Instant

data class Reservation(
    val status: Boolean,
    val name: String,
    val timeStamp: Instant = Instant.now()
)