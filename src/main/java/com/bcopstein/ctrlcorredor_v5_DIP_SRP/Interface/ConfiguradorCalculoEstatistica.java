package com.bcopstein.ctrlcorredor_v5_DIP_SRP.Interface;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.CalculoDesconsidera;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.CalculoOriginal;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.ICalculoEstatististica;

/**
 * ConfiguradorCalculoEstatistica
 */
@Configuration
public class ConfiguradorCalculoEstatistica {
    @Bean
    @Primary
    @Qualifier("original")
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "original", matchIfMissing = true)
    public ICalculoEstatististica opcaoRegraClassica(){
        return new CalculoOriginal();
    }
    
    @Bean
    @Primary
    @Qualifier("desconsidera")
    @ConditionalOnProperty(name = "calculo.estatistica", havingValue = "desconsidera")
    public ICalculoEstatististica opcaoDesconsidera(){
        return new CalculoDesconsidera();
    }
    
    
}