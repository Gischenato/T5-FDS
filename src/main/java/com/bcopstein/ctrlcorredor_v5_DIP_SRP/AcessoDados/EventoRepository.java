package com.bcopstein.ctrlcorredor_v5_DIP_SRP.AcessoDados;

import java.util.List;

import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.Evento;
import com.bcopstein.ctrlcorredor_v5_DIP_SRP.LogicaNegocios.IEventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventoRepository implements IEventoRepository{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EventoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;  
        this.jdbcTemplate.execute("DROP TABLE eventos IF EXISTS");
        this.jdbcTemplate.execute("CREATE TABLE eventos("
                + "id int, nome VARCHAR(255), dia int, mes int, ano int, distancia int, horas int, minutos int, segundos int, PRIMARY KEY(id), cpf VARCHAR(255))");
        // Insere eventos
        cadastra(new Evento(1,"Poa Day Run",22,5,2019,5,0,35,32, "85632532"));
        cadastra(new Evento(2,"Poa Night Run",12,6,2019,5,0,31,10, "10001287"));
        cadastra(new Evento(3,"Winter Day Run",9,7,2019,5,0,29,17, "85632532"));
        cadastra(new Evento(4,"Summer Night Run",18,12,2019,5,0,32,25, "10001287"));
    }  

    public List<Evento> todos(String cpf) {
        String sql = String.format("SELECT * from eventos WHERE cpf='%s'", cpf);
        List<Evento> resp = this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new Evento(rs.getInt("id"), rs.getString("nome"), rs.getInt("dia"), rs.getInt("mes"),
                        rs.getInt("ano"), rs.getInt("distancia"), rs.getInt("horas"), rs.getInt("minutos"),
                        rs.getInt("segundos"), rs.getString("cpf")));
        return resp;
    }

    public boolean cadastra(Evento evento){
        this.jdbcTemplate.update(
            "INSERT INTO eventos(id,nome,dia,mes,ano,distancia,horas,minutos,segundos,cpf) VALUES (?,?,?,?,?,?,?,?,?,?)",
            evento.getId(), evento.getNome(), evento.getDia(), evento.getMes(), evento.getAno(),
            evento.getDistancia(), evento.getHoras(), evento.getMinutos(), evento.getSegundos(), evento.getCpf());
        return true;
    }
}
