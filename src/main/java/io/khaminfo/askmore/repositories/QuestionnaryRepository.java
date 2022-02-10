package io.khaminfo.askmore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.khaminfo.askmore.domain.Questionnary;
@Repository
public interface QuestionnaryRepository extends CrudRepository<Questionnary, Long> {
	 Questionnary getById(long id);

}
