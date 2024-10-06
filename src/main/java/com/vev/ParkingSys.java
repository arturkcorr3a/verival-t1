package com.vev;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class ParkingSys {
    private boolean isVip;
    private static double oneHourPrice = 5.90;
    private static double additionalHourPrice = 2.50;
    private static double overnightPrice = 50.00;
    private LocalDate entranceDate;
    private LocalTime entranceTime;

    public ParkingSys(boolean isVip) {
        this.isVip = isVip;
    }

    public String enter() {
        LocalTime currentTime = LocalTime.now();
        verifyRange(currentTime);

        this.entranceTime = currentTime;
        this.entranceDate = LocalDate.now();

        return String.format("Entrada realizada em %s às %s", entranceDate, entranceTime);
    }

    private double price(LocalDate exitDate, LocalTime exitTime) {
        verifyRange(exitTime);
        double valor;

        // 15min cortesia
        if (entranceDate.equals(exitDate) && entranceTime.plusMinutes(15).isAfter(exitTime)) {
            return 0.0;
        }

        // Verifica pernoite
        valor = overnightCalculator(exitDate, exitTime);
        if ((valor > 0.0)) {
            return (isVip) ? valor * 0.5 : valor;
        }

        // Verifica se é menos de 1h
        if (entranceTime.plusHours(1).isAfter(exitTime)) {
            return (isVip) ? oneHourPrice * 0.5 : oneHourPrice;
        }

        // Calcula valor
        long durationInSeconds = Duration.between(entranceTime, exitTime).getSeconds();
        long durationInHours = (int) Math.ceil(durationInSeconds / 3600.0);
        valor = oneHourPrice + (durationInHours - 1) * additionalHourPrice;
        return (isVip) ? valor * 0.5 : valor;

    }

    public String leave() {
        LocalDate exitDate = LocalDate.now();
        LocalTime exitTime = LocalTime.now();

        // verifica se entrou antes de sair
        if (entranceDate.isAfter(exitDate) || (entranceDate.equals(exitDate) && entranceTime.isAfter(exitTime))) {
            throw new RuntimeException("Entrance is after exit");
        }

        verifyRange(exitTime);

        double price = price(exitDate, exitTime);

        return String.format("Saída realizada em %s às %s.%n Valor a pagar: R$ %.2f", entranceDate, entranceTime,
                price);
    }

    private double overnightCalculator(LocalDate exitDate, LocalTime exitTime) {
        if (entranceDate.equals(exitDate)) {
            if (entranceTime.isBefore(LocalTime.of(2, 0)) && exitTime.isAfter(LocalTime.of(8, 0))) {
                return overnightPrice;
            }

            return 0.0;
        }

        if (exitDate.isAfter(entranceDate)) {
            long daysBetween = ChronoUnit.DAYS.between(entranceDate, exitDate);

            if (entranceTime.isBefore(LocalTime.of(2, 0))) {
                if (exitTime.isAfter(LocalTime.of(8, 0))) {
                    return overnightPrice * (daysBetween + 1);
                }

                return overnightPrice * daysBetween;
            }

            if (exitTime.isAfter(LocalTime.of(8, 0))) {
                return overnightPrice * daysBetween;
            }

            return overnightPrice * (daysBetween - 1);

        }

        throw new IllegalArgumentException("Entrance date is after exit date");
    }

    public void verifyRange(LocalTime time) {
        if (time.isAfter(LocalTime.of(2, 0)) && time.isBefore(LocalTime.of(8, 0))) {
            throw new RuntimeException("O estacionamento está fechado neste horário");
        }
    }

}