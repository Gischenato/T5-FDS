package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CalculoDesconsidera implements ICalculoEstatististica {
    @Override
    public EstatisticasDTO calculaEstatisticas(IEventoRepository eventoRep, double distancia) {
        List<Evento> eventos = eventoRep.todos()
                .stream()
                .filter(e -> e.getDistancia() == distancia)
                .collect(Collectors.toList());
        // Obtém um stream com os valores ordenados

        List<Double> valores = eventos
                .stream()
                .map(e -> e.getHoras() * 60 * 60 + e.getMinutos() * 60.0 + e.getSegundos())
                .sorted()
                .collect(Collectors.toList());

        Collections.sort(valores);
        valores.remove(0);
        valores.remove(valores.size() - 1);

        double media = valores
                .stream()
                .mapToDouble(v -> v)
                .average()
                .orElse(Double.NaN);
        // Calcula mediana
        Double mediana = Double.NaN;
        if (valores.size() > 0) {
            mediana = ((valores.size() % 2 == 0)
                    ? (valores.get(valores.size() / 2 - 1)) + (valores.get(valores.size() / 2)) / 2.0
                    : (valores.get(valores.size() / 2)));
        }
        // Calcula o desvio padrao
        double varianca;
        double desvioPadrao = Double.NaN;
        if (mediana != Double.NaN) {
            varianca = valores
                    .stream()
                    .mapToDouble(v -> v - media)
                    .map(v -> v * v)
                    .average().getAsDouble();
            desvioPadrao = Math.sqrt(varianca);
        }
        return new EstatisticasDTO(media, mediana, desvioPadrao);
    }
}
