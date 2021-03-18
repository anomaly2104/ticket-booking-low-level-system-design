package com.uditagarwal.model;

import com.uditagarwal.exceptions.InvalidStateException;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Payment {
    private final String paymentId;
    private final double amountPaid;
    private PaymentStatus paymentStatus;

    public Payment(@NonNull final String paymentId, final double amountPaid) {
        this.paymentId = paymentId;
        this.amountPaid = amountPaid;
        paymentStatus = PaymentStatus.Initiated;
    }

    public void confirmPayment() {
        if (this.paymentStatus != PaymentStatus.Initiated) {
            throw new InvalidStateException();
        }
        this.paymentStatus = PaymentStatus.Success;
    }

    public void failedPayment() {
        if (this.paymentStatus != PaymentStatus.Initiated) {
            throw new InvalidStateException();
        }
        this.paymentStatus = PaymentStatus.Failed;
    }
}
