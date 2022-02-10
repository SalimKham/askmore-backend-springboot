package io.khaminfo.askmore.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.UserInfo;

@Repository
public interface  ProfileRepository extends CrudRepository<UserInfo, Long> {

	public Optional<UserInfo> findById(Long id);
	 @Modifying
	  @Transactional
	  @Query("update UserInfo u set u.photo = :photo where u.id_Info = :id ")
	  int updateProfilePicture( @Param("id") long id, 
	    @Param("photo") String photo);

}
