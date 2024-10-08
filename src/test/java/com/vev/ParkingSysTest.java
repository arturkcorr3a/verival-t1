package com.vev;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSysTest {
    //testes de caso limite
    @Test
    public void testEntradaESaidaNoMesmoMinuto() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0, 0);
        LocalTime exitTime = LocalTime.of(10, 0, 30);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 0,00"));
    }

    @Test
    public void testEntradaESaidaNoMesmoMinutoVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0, 0);
        LocalTime exitTime = LocalTime.of(10, 0, 30);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 0,00"));
    }


    @Test
    public void testPermanenciaExataDe15Minutos() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusMinutes(15);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 0,00"));
    }

    @Test
    public void testPermanenciaExataDe15MinutosVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusMinutes(15);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 0,00"));
    }

    @Test
    public void testPermanenciaExataDeUmaHora() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusHours(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 5,90"));
    }

    @Test
    public void testPermanenciaExataDeUmaHoraVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusHours(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 2,95"));
    }

    @Test
    public void testPermanenciaDe16Minutos() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusMinutes(16);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 5,90"));
    }

    @Test
    public void testPermanenciaDe16MinutosVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusMinutes(16);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 2,95"));
    }

    @Test
    public void testPermanenciaDe15MinutosE1Segundo() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.of(2024, 10, 8);
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusMinutes(15).plusSeconds(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertEquals("Saída realizada em 08/10/2024 às 10:15:01.\nValor a pagar: R$ 5,90", resultado);
    }

    @Test
    public void testPermanenciaDe15MinutosE1SegundoVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.of(2024, 10, 8);
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusMinutes(15).plusSeconds(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertEquals("Saída realizada em 08/10/2024 às 10:15:01.\nValor a pagar: R$ 2,95", resultado);
    }

    @Test
    public void testPermanenciaDeUmaHoraEUmMinuto() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusHours(1).plusMinutes(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 8,40"));
    }

    @Test
    public void testPermanenciaDeUmaHoraEUmMinutoVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusHours(1).plusMinutes(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 4,20"));
    }

    @Test
    public void testPermanenciaDeUmaHoraUmSegundo() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusHours(1).plusSeconds(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 8,40"));
    }

    @Test
    public void testPermanenciaDeUmaHoraUmSegundoVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.plusHours(1).plusSeconds(1);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 4,20"));
    }

    //teste de mudanças de dias
    @Test
    public void testEntradaAntesDaMeiaNoiteSaidaDepois() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(23, 50);
        LocalTime exitTime = LocalTime.of(0, 10);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 5,90"));
    }

    @Test
    public void testEntradaAntesDaMeiaNoiteSaidaDepoisVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(23, 50);
        LocalTime exitTime = LocalTime.of(0, 10);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 2,95"));
    }

    @Test
    public void testEntradaSaidaDiasDiferentesPermanenciaCurta() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(23, 55);
        LocalTime exitTime = LocalTime.of(0, 5);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 0,00"));
    }

    @Test
    public void testEntradaSaidaDiasDiferentesPermanenciaCurtaVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(23, 55);
        LocalTime exitTime = LocalTime.of(0, 5);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 0,00"));
    }

    //testes cálculos de valor

    @Test
    public void testPermanenciaDeMultiplasHoras() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(8, 0);
        LocalTime exitTime = entranceTime.plusHours(5);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 15,90"));
    }

    @Test
    public void testPermanenciaDeMultiplasHorasVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(8, 0);
        LocalTime exitTime = entranceTime.plusHours(5);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 7,95"));
    }

    @Test
    public void testFalsePernoite() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(1, 0);
        LocalTime exitTime = LocalTime.of(9, 0);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 23,40"));
    }

    @Test
    public void testFalsePernoiteVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(1, 0);
        LocalTime exitTime = LocalTime.of(9, 0);

        parking.enter(date, entranceTime);
        String resultado = parking.leave(date, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 11,70"));
    }

    @Test
    public void testUmPernoiteDoisDias() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(12, 0);
        LocalTime exitTime = LocalTime.of(23, 0);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 50,00"));
    }

    @Test
    public void testUmPernoiteDoisDiasVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(12, 0);
        LocalTime exitTime = LocalTime.of(23, 0);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 25,00"));
    }

    @Test
    public void testDoisPernoitesDoisDia() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(1, 0);
        LocalTime exitTime = LocalTime.of(9, 0);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 100,00"));
    }

    @Test
    public void testDoisPernoitesDoisDiaVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(1);
        LocalTime entranceTime = LocalTime.of(1, 0);
        LocalTime exitTime = LocalTime.of(9, 0);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 50,00"));
    }

    @Test
    public void testDoisPernoitesDoisDias() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(2);
        LocalTime entranceTime = LocalTime.of(1, 0);
        LocalTime exitTime = LocalTime.of(9, 0);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 150,00"));
    }

    @Test
    public void testDoisPernoitesDoisDiasVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate entranceDate = LocalDate.now();
        LocalDate exitDate = entranceDate.plusDays(2);
        LocalTime entranceTime = LocalTime.of(1, 0);
        LocalTime exitTime = LocalTime.of(9, 0);

        parking.enter(entranceDate, entranceTime);
        String resultado = parking.leave(exitDate, exitTime);

        assertTrue(resultado.contains("Valor a pagar: R$ 75,00"));
    }

    //testes de exceções
    @Test
    public void testSaidaAntesDaEntrada() {
        ParkingSys parking = new ParkingSys(false);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.minusMinutes(10);

        parking.enter(date, entranceTime);

        assertThrows(IllegalArgumentException.class, () -> parking.leave(date, exitTime));
    }

    @Test
    public void testSaidaAntesDaEntradaVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalDate date = LocalDate.now();
        LocalTime entranceTime = LocalTime.of(10, 0);
        LocalTime exitTime = entranceTime.minusMinutes(10);

        parking.enter(date, entranceTime);

        assertThrows(IllegalArgumentException.class, () -> parking.leave(date, exitTime));
    }

    @Test
    public void testHorarioFechado() {
        ParkingSys parking = new ParkingSys(false);
        LocalTime fechado = LocalTime.of(3, 0);

        assertThrows(IllegalArgumentException.class, () -> parking.verifyRange(fechado));
    }

    @Test
    public void testHorarioFechadoVIP() {
        ParkingSys parking = new ParkingSys(true);
        LocalTime fechado = LocalTime.of(3, 0);

        assertThrows(IllegalArgumentException.class, () -> parking.verifyRange(fechado));
    }
}