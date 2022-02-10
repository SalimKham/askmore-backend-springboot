package io.khaminfo.askmore.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.Field;
@Repository
public interface FieldRepository extends CrudRepository<Field,Long>{
  Field getById(Long id );
  @Modifying
  @Transactional
  @Query("update Field u set u.is_arrchived = :value where u.id = :id ")
  int updateFiedState(  @Param("id") long id , @Param("value") boolean value);
}
