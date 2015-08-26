package sample.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import sample.Person;

public interface PersonRepository extends
	PagingAndSortingRepository<Person, String> {

}
