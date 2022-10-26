package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicoEstatistica {
    private IEventoRepository eventoRep;
    private ICalculoEstatististica calculoEstatisticaStrategy;

    @Autowired
    // REFATORAR: necessita uma interface específica
    // Esta desrespeitando SRP, afinal IEventoResitory tem o método create que não
    // é necessário para ServicoEstatistica. Além disseo este serviço poderia contar
    // com outras operações como por exemplo buscar apenas os eventos de uma
    // certa distancia
    public ServicoEstatistica(IEventoRepository eventoRepository, ICalculoEstatististica calculoEstatisticaStrategy) {
        this.eventoRep = eventoRepository;
        this.calculoEstatisticaStrategy = calculoEstatisticaStrategy;
    }

    public EstatisticasDTO calculaEstatisticas(int distancia){
        // Seleciona os eventos da distancia informada
        List<Evento> eventos = 
            eventoRep.todos()
                .stream()
                .filter(e->e.getDistancia() == distancia)
                .collect(Collectors.toList());
        // Obtém um stream com os valores ordenados
        List<Double> valores = eventos
            .stream()
            .map(e-> e.getHoras()*60*60 + e.getMinutos()*60.0 + e.getSegundos())
            .sorted()
            .collect(Collectors.toList());
            
        return calculoEstatisticaStrategy.calculaEstatisticas(valores);    
    }

    public PerformanceDTO calculaAumentoPerformance(int distancia,int ano){
        List<Evento> eventos = eventoRep
                        .todos()
                        .stream()
                        .filter(e->e.getAno() == ano)
                        .collect(Collectors.toList());
        int indiceMaiorDif = 0;
        double maiorDif = -1.0;
        for(int i=0;i<eventos.size()-1;i++){
            Evento e1 = eventos.get(i);
            Evento e2 = eventos.get(i+1);
            double tempo1  = e1.getHoras()*60*60 + e1.getMinutos()*60.0 + e1.getSegundos();
            double tempo2  = e2.getHoras()*60*60 + e2.getMinutos()*60.0 + e2.getSegundos();
            if ((tempo1-tempo2)>maiorDif){
                maiorDif = tempo1-tempo2;
                indiceMaiorDif = i;
            }
        }         
        return new PerformanceDTO(eventos.get(indiceMaiorDif).getNome(),
                                  eventos.get(indiceMaiorDif+1).getNome(),
                                  maiorDif);
    }
}
