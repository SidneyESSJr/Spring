package br.com.alura.springdata.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.projecoes.FuncionarioProjecao1;

public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario>{

    List<Funcionario> findByNomeContains(String nome);

    @Query("SELECT f FROM Funcionario f "
        + "WHERE f.nome LIKE '%'|| :nome || '%' "
        + "AND f.salario >= :salario "
        + "AND f.dataContratacao = :data")
    List<Funcionario> findByNomeDataSalarioDataContratacao(String nome, Double salario, LocalDate data);

    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true)
    List<Funcionario> findByDataAdmisaoMaior(LocalDate data);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
    List<FuncionarioProjecao1> findFuncionarioProjecao1();
}
