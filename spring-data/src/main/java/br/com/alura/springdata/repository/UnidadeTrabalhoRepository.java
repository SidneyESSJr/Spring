package br.com.alura.springdata.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.alura.springdata.orm.UnidadeTrabalho;

public interface UnidadeTrabalhoRepository
        extends PagingAndSortingRepository<UnidadeTrabalho, Integer>, JpaSpecificationExecutor<UnidadeTrabalho> {

}
