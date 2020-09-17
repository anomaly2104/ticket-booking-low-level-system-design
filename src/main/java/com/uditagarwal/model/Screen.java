package com.uditagarwal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.management.MXBean;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Screen {

    private final String id;
    private final String name;
    private final Theatre theatre;
    //Other screen metadata.

    private final List<Seat> seats;

    public Screen(@NonNull final String id, @NonNull final String name, @NonNull final Theatre theatre) {
        this.id = id;
        this.name = name;
        this.theatre = theatre;
        this.seats = new ArrayList<>();
    }

    public void addSeat(@NonNull final Seat seat) {
        this.seats.add(seat);
    }

}
