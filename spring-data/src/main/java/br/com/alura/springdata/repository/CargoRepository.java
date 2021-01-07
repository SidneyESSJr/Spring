package br.com.alura.springdata.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.springdata.orm.Cargo;

@Repository
public interface CargoRepository extends PagingAndSortingRepository<Cargo, Integer> {

}
