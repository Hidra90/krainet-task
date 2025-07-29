package by.krainet.task.repository;

import by.krainet.task.model.Person;
import by.krainet.task.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String userName);

    List<Person> findByRole(Role role);
}
