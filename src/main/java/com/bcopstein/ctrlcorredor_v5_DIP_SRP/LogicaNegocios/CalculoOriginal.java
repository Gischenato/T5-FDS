package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CalculoOriginal implements ICalculoEstatististica {
    @Override
    public EstatisticasDTO calculaEstatisticas(List<Double> valores) {
        double media = valores
        .stream()
        .mapToDouble(v->v)
        .average()
        .orElse(Double.NaN);
        // Calcula mediana
        Double mediana = Double.NaN;
        if (valores.size() > 0){
            mediana =
                ((valores.size() % 2 == 0) ?
                (valores.get(valores.size()/2 - 1))+(valores.get(valores.size()/2))/2.0 :
                (valores.get(valores.size()/2)));
        }
        // Calcula o desvio padrao
        double varianca;
        double desvioPadrao = Double.NaN;
        if (mediana != Double.NaN){
            varianca = valores
                .stream()
                .mapToDouble(v -> v - media)
                .map(v -> v*v)
                .average().getAsDouble();
            desvioPadrao = Math.sqrt(varianca);
        }
        return new EstatisticasDTO(media, mediana, desvioPadrao);
    }
}
