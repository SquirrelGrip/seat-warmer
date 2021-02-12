package com.github.squirrelgrip.seat.exception

import java.lang.RuntimeException
import java.time.LocalDate

class FullyBookedException(
    date: LocalDate
) : RuntimeException("There are no available reservation for $date")
