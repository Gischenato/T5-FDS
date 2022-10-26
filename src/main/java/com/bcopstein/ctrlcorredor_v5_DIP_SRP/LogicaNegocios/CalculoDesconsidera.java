package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CalculoDesconsidera implements ICalculoEstatististica {
    @Override
    public EstatisticasDTO calculaEstatisticas(List<Double> valores) {
        return new EstatisticasDTO(0, 0, 0);
    }
}
