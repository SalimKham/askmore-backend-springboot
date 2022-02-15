package io.khaminfo.askmore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.domain.Subject;
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
	@Query("select t from Tutorial t  where t.subject = :subject and t.id > :minID and t.id < :maxID")
	  List<Tutorial> getTutorialsBySubjectAndRange( @Param("subject") Subject subject,@Param("minID") long minID, @Param("maxID") long maxID);
	@Query("select t from Tutorial t  where t.id > :minID and t.id < :maxID ")
	  List<Tutorial> getTutorialsByRange( @Param("minID") long minID, @Param("maxID") long maxID);
}
