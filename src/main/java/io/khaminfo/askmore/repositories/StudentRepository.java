package io.khaminfo.askmore.repositories;

import org.springframework.data.repository.CrudRepository;

import io.khaminfo.askmore.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
