package com.uditagarwal.scenarios;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class ValidatePaymentTest extends BaseTest {
    @BeforeEach
    void setUp() {
        setupControllers(10, 0);
    }

    @Test
    void paymentSuccess() {
        String user = "User1";

        final String movie = movieController.createMovie("Movie 1");
        final String screen = setupScreen();
        final List<String> screen1SeatIds = createSeats(theatreController, screen, 12, 10);

        final String show = showController.createShow(movie, screen, new Date(), 2 * 60 * 60);

        List<String> u1AvailableSeats = showController.getAvailableSeats(show);

        // Validate that seats u received has all screen seats
        validateSeatsList(u1AvailableSeats, screen1SeatIds, ImmutableList.of());

        ImmutableList<String> u1SelectedSeats = ImmutableList.of(
                screen1SeatIds.get(0),
                screen1SeatIds.get(32),
                screen1SeatIds.get(68),
                screen1SeatIds.get(115)
        );
        final String bookingId = bookingController.createBooking(user, show, u1SelectedSeats);
        // 2 Silver - 0 and 32, 1 Gold - 68, 1 Diamond - 115 => 150*2 + 300 + 500 = 1100
        boolean paymentStatus = paymentsController.initiatePayment(bookingId, user, 1100);
        Assert.assertTrue(paymentStatus);
    }

    @Test
    void paymentFailed() {
        String user = "User1";

        final String movie = movieController.createMovie("Movie 1");
        final String screen = setupScreen();
        final List<String> screen1SeatIds = createSeats(theatreController, screen, 12, 10);

        final String show = showController.createShow(movie, screen, new Date(), 2 * 60 * 60);

        List<String> u1AvailableSeats = showController.getAvailableSeats(show);

        // Validate that seats u received has all screen seats
        validateSeatsList(u1AvailableSeats, screen1SeatIds, ImmutableList.of());

        ImmutableList<String> u1SelectedSeats = ImmutableList.of(
                screen1SeatIds.get(0),
                screen1SeatIds.get(32),
                screen1SeatIds.get(58),
                screen1SeatIds.get(115)
        );
        final String bookingId = bookingController.createBooking(user, show, u1SelectedSeats);
        // 2 Silver - 0 and 32, 1 Gold - 68, 1 Diamond - 115 => 150*2 + 300 + 500 = 1100
        boolean paymentStatus = paymentsController.initiatePayment(bookingId, user, 1000);
        Assert.assertFalse(paymentStatus);
    }
}
