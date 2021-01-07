package br.com.alura.springdata.specification;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.springdata.orm.UnidadeTrabalho;

public class SpecificationUnidadeTrabalho {

    public static Specification<UnidadeTrabalho> consultaCompletaUnidadeTrabalho(String descricao, String endereco) {
        return (root, criteriaQuery, criteriaBuillder) -> {
            Predicate restrictions = criteriaBuillder.and();

            if (descricao != null && !descricao.isEmpty()) {
                restrictions = criteriaBuillder.and(restrictions,
                        criteriaBuillder.like(root.get("descricao"), "%" + descricao + "%"));
            }

            if (endereco != null && !endereco.isEmpty()) {
                restrictions = criteriaBuillder.and(restrictions,
                        criteriaBuillder.like(root.get("endereco"), "%" + endereco + "%"));
            }
            return restrictions;
        };
    }
}
