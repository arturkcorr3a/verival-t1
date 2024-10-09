package com.vev;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import java.time.LocalTime;

public class ParkingSysTest {

    @ParameterizedTest
    @CsvSource({
            "true, 10:00, 10:10, 00.00", // ID 1
            "false, 10:00, 10:10, 00.00", // ID 2
            "true, 10:00, 10:45, 02.95", // ID 3
            "false, 10:00, 10:45, 05.90", // ID 4
            "true, 08:00, 13:00, 07.95", // ID 5
            "false, 08:00, 13:00, 15.90", //  ID 6
            "true, 09:00:00, 09:15:00, 00.00", // ID 9
            "false, 09:00:00, 09:15:00, 00.00", // ID 10
            "true, 09:00:00, 09:15:01, 02.95", // ID 11
            "false, 09:00:00, 09:15:01, 05.90", //  ID 12
            "true, 09:00:00, 10:00:00, 02.95", // ID 17
            "false, 09:00:00, 10:00:00, 05.90", // ID 18
            "true, 09:00:00, 10:00:01, 04.20", // ID 19
            "false, 09:00:00, 10:00:01, 08.40", //  ID 20
            "true, 10:00:00, 10:00:30, 00.00", // ID 31
            "false, 10:00:00, 10:00:30, 00.00", //  ID 32
            "true, 01:00:00, 09:00:00, 11.70", // ID 37
            "false, 01:00:00, 09:00:00, 23.40" // ID 38

    })

    public void testVariasHoras(boolean isVIP, String entrada, String saida, double valorEsperado) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.parse(entrada);
        LocalTime exitTime = LocalTime.parse(saida);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ " + String.format("%.2f", valorEsperado)));
    }

    @ParameterizedTest
    @CsvSource({
            "true, 12:00, 23:00, 25.00", // ID 7
            "false, 12:00, 23:00, 50.00", //  ID 8
            "true, 23:50:00, 00:00:10, 02.95", // ID 33
            "false, 23:50:00, 00:00:10, 05.90", //  ID 34
            "true, 23:59:00, 00:00:00, 00.00", // ID 35
            "false, 23:59:00, 00:00:00, 00.00" //  ID 36
    })
    public void testEntradaSaidaDiasDiferentes(boolean isVIP, String entrada, String saida, double valorEsperado) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.parse(entrada);
        LocalTime exitTime = LocalTime.parse(saida);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ " + String.format("%.2f", valorEsperado)));
    }

    @ParameterizedTest
    @CsvSource({
            "true, 09:00, 01:00, 50.00", // ID 39
            "false, 09:00, 01:00, 25.00" // ID 40
    })
    public void testPernoitesEOutrosDias(boolean isVIP, String entrada, String saida, double valorEsperado) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(2); // Teste para múltiplos dias
        LocalTime entranceTime = LocalTime.parse(entrada);
        LocalTime exitTime = LocalTime.parse(saida);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ " + String.format("%.2f", valorEsperado)));
    }

    @ParameterizedTest
    @CsvSource({
            "true, 10:00, 09:50, 0", // ID 27 Saída antes da entrada (horário)
            "false, 10:00, 09:50, 0", // ID 28
            "true, 10:00, 10:10, 1", // ID 29 Saída antes da entrada (dia)
            "false, 10:00, 10:10, 1" // ID 30
    })
    public void testSaidaAntesDaEntrada(boolean isVIP, String entrada, String saida, int deltaDays) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.minusDays(deltaDays);
        LocalTime entranceTime = LocalTime.parse(entrada);
        LocalTime exitTime = LocalTime.parse(saida);

        parking.enter(entranceDate, entranceTime);

        assertThrows(IllegalArgumentException.class, () -> parking.leave(exitDate, exitTime));
    }

    @ParameterizedTest
    @CsvSource({
            "true, 03:00", // ID 25 Horário fechado
            "false, 03:00" // ID 26
    })
    public void testHorarioFechado(boolean isVIP, String horarioFechado) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalTime fechado = LocalTime.parse(horarioFechado);

        assertThrows(IllegalArgumentException.class, () -> parking.verifyRange(fechado));
    }

}
