package com.github.squirrelgrip.seat.exception

import java.lang.RuntimeException
import java.time.LocalDate

class UnknownReservationException(
    date: LocalDate,
    name: String
): RuntimeException("No reservation for $name on $date")
