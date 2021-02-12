package com.github.squirrelgrip.seat.service

import com.github.squirrelgrip.seat.exception.FullyBookedException
import com.github.squirrelgrip.seat.exception.UnknownReservationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

internal class ReservationServiceTest {
    private val date = LocalDate.now()

    @Test
    fun reserveSeatForToday() {
        val testSubject = ReservationService(2)
        testSubject.reserve(date, "User")
        val reservation = testSubject.getReservation(date, "User")
        assertThat(reservation.status).isEqualTo(true)
        assertThat(reservation.name).isEqualTo("User")
        assertThat(testSubject.getStatus(date).available).isEqualTo(1)
        assertThat(testSubject.reservations[date]).containsExactly("User")
    }

    @Test
    fun reserve2SeatsForToday() {
        val testSubject = ReservationService(1)
        testSubject.reserve(date, "User")
        val reservation = testSubject.getReservation(date, "User")
        assertThat(reservation.status).isEqualTo(true)
        assertThat(reservation.name).isEqualTo("User")
        assertThat(testSubject.getStatus(date).available).isEqualTo(0)
        assertThat(testSubject.reservations[date]).containsExactly("User")

        assertThrows<FullyBookedException> {testSubject.reserve(date, "Another User")}
        assertThrows<UnknownReservationException> {testSubject.getReservation(date, "Another User")}

        assertThat(testSubject.getStatus(date).available).isEqualTo(0)
        assertThat(testSubject.reservations[date]).containsExactly("User")
    }

    @Test
    fun reserve2SeatsForSameUser() {
        val testSubject = ReservationService(2)
        testSubject.reserve(date, "User")
        testSubject.reserve(date, "User")
        assertThat(testSubject.getStatus(date).available).isEqualTo(1)
        assertThat(testSubject.reservations[date]).containsExactly("User")
    }

    @Test
    fun reserveSeatForTodayWhenNoAvailableSeats() {
        val testSubject = ReservationService(0)
        assertThrows<FullyBookedException> {testSubject.reserve(date, "User")}
        assertThat(testSubject.getStatus(date).available).isEqualTo(0)
        assertThat(testSubject.reservations[date]).isEmpty()
    }
}