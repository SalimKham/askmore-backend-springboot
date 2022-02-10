package io.khaminfo.askmore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.khaminfo.askmore.domain.LearningEvent;

@Repository
public interface LearningEventRepository extends CrudRepository<LearningEvent,Long> {
	LearningEvent getById(Long id);
	  
	
}
