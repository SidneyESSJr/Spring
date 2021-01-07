package br.com.alura.springdata.specification;

import java.time.LocalDate;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.springdata.orm.Funcionario;

public class SpecificationFuncionario {

    public static Specification<Funcionario> consultaCompletaFuncionario(String nome, String cpf, Double salario,
            LocalDate dataContratacao) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restrictions = criteriaBuilder.and();

            if (nome != null && !nome.isEmpty()) {
                restrictions = criteriaBuilder.and(restrictions,
                        criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
            }

            if (cpf != null && !cpf.isEmpty()) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("cpf"), cpf));
            }

            if (salario > 0) {
                restrictions = criteriaBuilder.and(restrictions,
                        criteriaBuilder.greaterThan(root.get("salario"), salario));
            }

            if (dataContratacao != null) {
                restrictions = criteriaBuilder.and(restrictions,
                        criteriaBuilder.greaterThan(root.get("dataContratacao"), dataContratacao));
            }
            return restrictions;
        };
    }
}
