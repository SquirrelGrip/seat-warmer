package com.github.squirrelgrip.seat.controller

import com.github.squirrelgrip.seat.model.Reservation
import com.github.squirrelgrip.seat.model.Status
import com.github.squirrelgrip.seat.service.ReservationService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class ReservationController(
    val reservationService: ReservationService
) {
    @GetMapping("/api/v1/reservation/{year}/{month}/{dayOfMonth}", produces = ["application/json"])
    fun getReservation(
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int,
        @PathVariable("dayOfMonth") dayOfMonth: Int,
        @RequestParam("name", required = true) name: String
    ): Reservation {
        val date = LocalDate.of(year, month, dayOfMonth)
        return reservationService.getReservation(date, name)
    }

    @PostMapping("/api/v1/reservation/{year}/{month}/{dayOfMonth}", produces = ["application/json"])
    fun makeReservation(
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int,
        @PathVariable("dayOfMonth") dayOfMonth: Int,
        @RequestParam("name", required = true) name: String
    ): Reservation {
        val date = LocalDate.of(year, month, dayOfMonth)
        reservationService.reserve(date, name)
        return reservationService.getReservation(date, name)
    }

    @GetMapping("/api/v1/reservations/{year}/{month}/{dayOfMonth}", produces = ["application/json"])
    fun getReservations(
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int,
        @PathVariable("dayOfMonth") dayOfMonth: Int
    ): List<String> {
        val date = LocalDate.of(year, month, dayOfMonth)
        return reservationService.reservations(date)
    }

    @GetMapping("/api/v1/status/{year}/{month}/{dayOfMonth}", produces = ["application/json"])
    fun getAvailable(
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int,
        @PathVariable("dayOfMonth") dayOfMonth: Int
    ): Status {
        val date = LocalDate.of(year, month, dayOfMonth)
        return reservationService.getStatus(date)
    }
}