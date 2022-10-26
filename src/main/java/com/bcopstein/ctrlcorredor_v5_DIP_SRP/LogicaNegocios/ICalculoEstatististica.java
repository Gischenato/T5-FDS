package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import java.util.List;

/**
 * ICalculoEstatististica
 */
public interface ICalculoEstatististica {
    public EstatisticasDTO calculaEstatisticas(List<Double> valores);
}