package com.vev;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;
import java.time.LocalTime;

public class ParkingSysTest {

    @ParameterizedTest
    @CsvSource({
            "false, 08:00, 09:00, 5.90", // 1 hora
            "true, 08:00, 09:00, 2.95", // 1 hora (VIP)
            "false, 08:00, 11:00, 10.90", // 3 horas
            "true, 08:00, 11:00, 5.45", // 3 horas (VIP)
            "false, 22:00, 23:00, 5.90", // 1 hora à noite
            "true, 22:00, 23:00, 2.95" // 1 hora à noite (VIP)
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
            "false, 23:50, 00:10, 5.90",
            "true, 23:50, 00:10, 2.95",
            "false, 23:55, 00:05, 0.00",
            "true, 23:55, 00:05, 0.00"
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
            "false, 12:00, 23:00, 100.00", // 2 pernoites
            "true, 12:00, 23:00, 50.00",
            "false, 01:00, 09:00, 100.00", // 2 dias e 2 pernoites
            "true, 01:00, 09:00, 50.00",
            "false, 09:00, 01:00, 50.00", // 2 dias e 1 pernoite
            "true, 09:00, 01:00, 25.00"
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
            "false, 10:00, 09:50", // Saída antes da entrada
            "true, 10:00, 09:50"
    })
    public void testSaidaAntesDaEntrada(boolean isVIP, String entrada, String saida) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.parse(entrada);
        LocalTime exitTime = LocalTime.parse(saida);

        parking.enter(date, entranceTime);

        assertThrows(IllegalArgumentException.class, () -> parking.leave(date, exitTime));
    }

    @ParameterizedTest
    @CsvSource({
            "false, 03:00", // Horário fechado
            "true, 03:00"
    })
    public void testHorarioFechado(boolean isVIP, String horarioFechado) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalTime fechado = LocalTime.parse(horarioFechado);

        assertThrows(IllegalArgumentException.class, () -> parking.verifyRange(fechado));
    }

    @ParameterizedTest
    @CsvSource({
            "false, 08:00, 13:00, 15.90", // 5 horas
            "true, 08:00, 13:00, 7.95",
            "false, 01:00, 09:00, 23.40", // 8 horas (sem pernoite)
            "true, 01:00, 09:00, 11.70"
    })
    public void testPermanenciaDeMultiplasHoras(boolean isVIP, String entrada, String saida, double valorEsperado) {
        ParkingSys parking = new ParkingSys(isVIP);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.parse(entrada);
        LocalTime exitTime = LocalTime.parse(saida);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ " + String.format("%.2f", valorEsperado)));
    }
}
