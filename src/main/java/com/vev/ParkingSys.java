package com.vev;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ParkingSys {
    private final boolean isVip;
    private static final double oneHourPrice = 5.90;
    private static final double additionalHourPrice = 2.50;
    private static final double overnightPrice = 50.00;
    private LocalDate entranceDate;
    private LocalTime entranceTime;
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ParkingSys(boolean isVip) {
        this.isVip = isVip;
    }

    public String enter() {
        return enter(LocalDate.now(), LocalTime.now());
    }

    public String enter(LocalDate currentDate, LocalTime currentTime) {
        verifyRange(currentTime);

        this.entranceTime = currentTime;
        this.entranceDate = currentDate;

        return String.format("Entrada realizada em %s às %s", entranceDate.format(dateFormatter),
                entranceTime.format(timeFormatter));
    }

    private double price(LocalDate exitDate, LocalTime exitTime) {
        verifyRange(exitTime);

        LocalDateTime entranceDateTime = LocalDateTime.of(entranceDate, entranceTime);
        LocalDateTime exitDateTime = LocalDateTime.of(exitDate, exitTime);

        // 15 minutos de cortesia
        if (Duration.between(entranceDateTime, exitDateTime).toMinutes() <= 15) {
            return 0.0;
        }

        // Verifica se é pernoite
        double valor = overnightCalculator(exitDate, exitTime);
        if (valor > 0.0) {
            return applyVipDiscount(valor);
        }

        // Calcula permanência total em horas, considerando data e hora
        long totalHours = ChronoUnit.HOURS.between(entranceDateTime, exitDateTime);

        // Verifica se é menos de 1 hora
        if (totalHours < 1) {
            return applyVipDiscount(oneHourPrice);
        }

        // Calcula valor total para horas adicionais
        valor = oneHourPrice + (totalHours - 1) * additionalHourPrice;
        return applyVipDiscount(valor);
    }

    public String leave() {
        return leave(LocalDate.now(), LocalTime.now());
    }

    public String leave(LocalDate exitDate, LocalTime exitTime) {
        // Verifica se entrou antes de sair
        if (entranceDate.isAfter(exitDate) || (entranceDate.equals(exitDate) && entranceTime.isAfter(exitTime))) {
            throw new RuntimeException("Entrance is after exit");
        }

        verifyRange(exitTime);

        double price = price(exitDate, exitTime);

        return String.format("Saída realizada em %s às %s.%nValor a pagar: R$ %.2f", exitDate.format(dateFormatter),
                exitTime.format(timeFormatter),
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

    private double applyVipDiscount(double valor) {
        return isVip ? valor * 0.5 : valor;
    }
}
