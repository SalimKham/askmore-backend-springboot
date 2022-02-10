package io.khaminfo.askmore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.khaminfo.askmore.domain.Question;

@Repository
public interface QuestionRepository  extends CrudRepository<Question, Long>{

}
