package com.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalculadoraEstacionamento {

    private static final double VALOR_HORA_INICIAL = 5.90;
    private static final double VALOR_HORA_ADICIONAL = 2.50;
    private static final double VALOR_PERNOITE = 50.00;
    private static final int MINUTOS_CORTESIA = 15;
    private static final LocalTime HORA_FECHA = LocalTime.of(2, 0);
    private static final LocalTime HORA_ABRE = LocalTime.of(8, 0);

    public static double calcularValor(LocalDateTime entrada, LocalDateTime saida, boolean isVip) {
LocalTime entradaTime = entrada.toLocalTime();
LocalTime saidaTime = saida.toLocalTime();

if (entradaTime.isBefore(HORA_ABRE) && entradaTime.isAfter(HORA_FECHA)) {
    throw new IllegalArgumentException("Horário de entrada inválido");
}

if (saidaTime.isBefore(HORA_ABRE) && saidaTime.isAfter(HORA_FECHA)) {
    throw new IllegalArgumentException("Horário de saída inválido");
}

        long totalMinutos = ChronoUnit.MINUTES.between(entrada, saida);

        if (totalMinutos <= MINUTOS_CORTESIA) {
            return 0.0;
        }

        long diasDiferenca = ChronoUnit.DAYS.between(entrada.toLocalDate(), saida.toLocalDate());
        int quantidadePernoites = 0;

        if (diasDiferenca > 0) {
            if (saida.toLocalTime().isBefore(LocalTime.of(8, 0))) {
                quantidadePernoites = (int) diasDiferenca - 1;
            } else {
                quantidadePernoites = (int) diasDiferenca;
            }
            if (quantidadePernoites < 0) {
                quantidadePernoites = 0;
            }
        }

        double valor = 0.0;
        if (quantidadePernoites > 0) {
            valor = VALOR_PERNOITE * quantidadePernoites;
        } else {
            // Cálculo normal
            if (totalMinutos <= 60) {
                valor = VALOR_HORA_INICIAL;
            } else {
                double horasAdicionais = Math.ceil((totalMinutos - 60) / 60.0);
                valor = VALOR_HORA_INICIAL + VALOR_HORA_ADICIONAL * horasAdicionais;
            }
        }
        if (isVip) {
            valor *= 0.5;
        }

        return valor;
    }
}
