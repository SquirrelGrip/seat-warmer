package com.github.squirrelgrip.seat.service

import com.github.squirrelgrip.seat.exception.FullyBookedException
import com.github.squirrelgrip.seat.exception.UnknownReservationException
import com.github.squirrelgrip.seat.model.Reservation
import com.github.squirrelgrip.seat.model.Status
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationService(
    val maximumReservations: Int = 2
) {
    val availablePlacesDateMap: MutableMap<LocalDate, Int> = mutableMapOf()
    val reservations: MutableMap<LocalDate, MutableList<String>> = mutableMapOf()

    fun getReservation(date: LocalDate, name: String): Reservation {
        if (reservations[date]?.contains(name) == true) {
            return Reservation(true, name)
        }
        throw UnknownReservationException(date, name)
    }

    fun reserve(date: LocalDate, name: String) {
        val availablePlaces = availablePlacesDateMap[date] ?: maximumReservations
        val list = reservations.putIfAbsent(date, mutableListOf())
        if (reservations[date]!!.contains(name)) {
            // Do Nothing
        } else if (availablePlaces > 0) {
            if (!reservations[date]!!.contains(name)) {
                availablePlacesDateMap[date] = availablePlaces - 1
                reservations[date]!!.add(name)
            }
        } else {
            throw FullyBookedException(date)
        }
    }

    fun getStatus(date: LocalDate): Status =
        Status(availablePlacesDateMap[date] ?: maximumReservations, maximumReservations)

    fun reservations(date: LocalDate): List<String> =
        reservations[date] ?: emptyList()

}