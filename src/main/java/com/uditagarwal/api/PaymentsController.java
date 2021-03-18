package com.uditagarwal.api;

import com.uditagarwal.model.PaymentStatus;
import com.uditagarwal.services.BookingService;
import com.uditagarwal.services.PaymentsService;
import lombok.NonNull;

public class PaymentsController {
    private final PaymentsService paymentsService;
    private final BookingService bookingService;

    public PaymentsController(PaymentsService paymentsService, BookingService bookingService) {
        this.paymentsService = paymentsService;
        this.bookingService = bookingService;
    }

    public void paymentFailed(@NonNull final String bookingId, @NonNull final String user) {
        paymentsService.processPaymentFailed(bookingService.getBooking(bookingId), user);
    }

    public void paymentSuccess(@NonNull final  String bookingId, @NonNull final String user) {
        bookingService.confirmBooking(bookingService.getBooking(bookingId), user);
    }

    public boolean initiatePayment(@NonNull final String bookingId, @NonNull final String user,
                                   @NonNull final double amountPaid) {
        PaymentStatus paymentStatus = paymentsService.initiatePayment(bookingService.getBooking(bookingId),
                user, amountPaid);
        if (paymentStatus == PaymentStatus.Success) {
            paymentSuccess(bookingId, user);
            return true;
        }

        if (paymentStatus == PaymentStatus.Failed) {
            paymentFailed(bookingId, user);
        }
        return false;
    }

}
