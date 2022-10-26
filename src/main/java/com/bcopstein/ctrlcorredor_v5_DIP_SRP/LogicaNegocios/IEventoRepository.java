package com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios;

import java.util.List;

public interface IEventoRepository {
    List<Evento> todos(String cpf);
    boolean cadastra(Evento evento);
}
