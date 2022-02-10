package io.khaminfo.askmore.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.Tutorial;

public interface TutorialRepository extends CrudRepository<Tutorial,Long>{
	Tutorial getById(Long id);
	@Modifying
	  @Transactional
	  @Query("update Tutorial u set u.nbrComment = u.nbrComment+1 where u.id = :id")
	  int updateNbrComments(  @Param("id") long id) ;
	@Modifying
	  @Transactional
	  @Query("update Tutorial u set u.nbrVisitor = u.nbrVisitor+1 where u.id = :id")
	  int updateNbrVisits(  @Param("id") long id) ;
}
