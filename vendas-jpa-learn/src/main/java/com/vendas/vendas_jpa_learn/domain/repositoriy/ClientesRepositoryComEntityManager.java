package com.vendas.vendas_jpa_learn.domain.repositoriy;

import com.vendas.vendas_jpa_learn.domain.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientesRepositoryComEntityManager {

    private static  String INSERT = "insert into cliente (nome) values (?)";
    private static  String SELECT_ALL = "SELECT * FROM CLIENTE";
    private static  String UPDATE = "UPDATE CLIENTE SET nome=? where id=?";
    private static  String DELETE = "DELETE FROM CLIENTE where id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente){
        // jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        //jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente){
       // jdbcTemplate.update(DELETE, new  Object[]{cliente.getId()});
        if(!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome){
       // return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"), new Object[]{"%"+nome+"%"}, obterClienteMapper());

        String jpql = "select c from Cliente c where c.nome = :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }

    @Transactional
    public List<Cliente> obterTodos(){
        //return  jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
    }

    private static RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");

                return new Cliente(id, nome);
            }
        };
    }
}
