package com.uditagarwal.services;

import com.google.common.collect.ImmutableMap;
import com.uditagarwal.exceptions.BadRequestException;
import com.uditagarwal.model.*;
import com.uditagarwal.providers.SeatLockProvider;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PaymentsService {

    Map<Booking, Integer> bookingFailures;
    private final Integer allowedRetries;
    private final SeatLockProvider seatLockProvider;
    private final Map<SeatType, Double> seatTypeAmountMap;

    public PaymentsService(@NonNull final Integer allowedRetries, SeatLockProvider seatLockProvider) {
        this.allowedRetries = allowedRetries;
        this.seatLockProvider = seatLockProvider;
        bookingFailures = new HashMap<>();
        seatTypeAmountMap = ImmutableMap.of(SeatType.Silver, 150.0, SeatType.Gold,
                300.0, SeatType.Diamond, 500.0);
    }

    public void processPaymentFailed(@NonNull final Booking booking, @NonNull final String user) {
        if (!booking.getUser().equals(user)) {
            throw new BadRequestException();
        }
        if (!bookingFailures.containsKey(booking)) {
            bookingFailures.put(booking, 0);
        }
        final Integer currentFailuresCount = bookingFailures.get(booking);
        final Integer newFailuresCount = currentFailuresCount + 1;
        bookingFailures.put(booking, newFailuresCount);
        if (newFailuresCount > allowedRetries) {
            seatLockProvider.unlockSeats(booking.getShow(), booking.getSeatsBooked(), booking.getUser());
        }
    }

    public PaymentStatus initiatePayment(@NonNull final Booking booking, @NonNull final String user,
                                         final double amountPaid) {
        if (!booking.getUser().equals(user)) {
            throw new BadRequestException();
        }
        final String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(paymentId, amountPaid);
        boolean paymentSucceeded = validatePayment(booking.getSeatsBooked(), amountPaid);
        if (paymentSucceeded) {
            payment.confirmPayment();
        } else {
            payment.failedPayment();
        }
        return payment.getPaymentStatus();
    }

    private boolean validatePayment(@NonNull final List<Seat> seatsBooked, final double amountPaid) {
        double costAmount = seatsBooked.stream().mapToDouble(seat -> seatTypeAmountMap.get(seat.getSeatType())).sum();
        return BigDecimal.valueOf(costAmount).compareTo(BigDecimal.valueOf(amountPaid)) == 0;
    }
}
