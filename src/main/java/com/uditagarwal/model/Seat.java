package com.uditagarwal.model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Seat {

    private final String id;
    private final int rowNo;
    private final int seatNo;
    private final SeatType seatType;

    public Seat(@NonNull final String id, final int rowNo, final int seatNo) {
        this.id = id;
        this.rowNo = rowNo;
        this.seatNo = seatNo;
        this.seatType = getSeatType(rowNo);
    }

    private SeatType getSeatType(final int rowNo) {
        if (rowNo <= 5) {
            return SeatType.Silver;
        }
        if (rowNo <= 10) {
            return SeatType.Gold;
        }
        return SeatType.Diamond;
    }
}
