package com.vev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

public class ParkingSysTest {

    @Test
    public void testExitNextDayWithin15Minutes() {
        ParkingSys parking = new ParkingSys(false); // Não é VIP
        parking.enter(LocalDate.of(2024, 10, 7), LocalTime.of(23, 59)); // Entrada às 23:59
        String result = parking.leave(LocalDate.of(2024, 10, 8), LocalTime.of(0, 10)); // Saída às 00:10 do dia seguinte

        assertEquals("Saída realizada em 08/10/2024 às 00:10:00.\nValor a pagar: R$ 0,00", result);
    }

    @Test
    public void testVipExitNextDayWithin15Minutes() {
        ParkingSys parking = new ParkingSys(true); // Cliente VIP
        parking.enter(LocalDate.of(2024, 10, 7), LocalTime.of(23, 59)); // Entrada às 23:59
        String result = parking.leave(LocalDate.of(2024, 10, 8), LocalTime.of(0, 10)); // Saída às 00:10 do dia seguinte

        assertEquals("Saída realizada em 08/10/2024 às 00:10:00.\nValor a pagar: R$ 0,00", result);
    }
}