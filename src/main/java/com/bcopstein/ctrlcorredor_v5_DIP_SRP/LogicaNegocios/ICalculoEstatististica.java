package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

/**
 * ICalculoEstatististica
 */
public interface ICalculoEstatististica {
    public EstatisticasDTO calculaEstatisticas(IEventoRepository eventoRep, double distancia);
}