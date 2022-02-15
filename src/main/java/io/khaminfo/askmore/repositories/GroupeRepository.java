package io.khaminfo.askmore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.Groupe;

@Repository
public interface GroupeRepository extends CrudRepository<Groupe, Long> {

	Groupe getById(long id);
	
	
	  @Modifying
	  @Transactional
	  @Query("update Groupe g set g.state = :state where g.id = :id ")
	int updateGroupeState( @Param("id") long id , @Param("state") int state);
	
	  @Modifying
	  @Transactional
	  @Query("update Groupe g set g.acceptedUsers = :value where g.id = :id ")
	int updateAcceptedStudent( @Param("id") long id , @Param("value") String value);

	  @Query("select g from Groupe g  where g.owner = :id ")
    List<Groupe> getByOwnerId(Long id);

}
