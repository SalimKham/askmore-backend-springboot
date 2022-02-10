package io.khaminfo.askmore.repositories;

import java.util.Date;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.Person;

@Repository
public interface UserRepository extends CrudRepository<Person, Long>{
  Person findByUsername(String username);
  @Override
  Iterable<Person> findAll() ;
  Person getById(Long id);
  Person findByIdAndConfirmPassword(long id , String confirmPassword);
  @Modifying
  @Transactional
  @Query("update Person u set u.user_state = 4 , u.confirmPassword = :code where u.id = :id")
  int updateUserConirmation(@Param("code") String code, 
    @Param("id") long id);
  @Modifying
  @Transactional
  @Query("update Person u set u.password = :password where u.username = :name ")
  int updateUserPassword( @Param("password") String newPassword,    @Param("name") String name);
  @Modifying
  @Transactional
  @Query("update Person u set u.user_state = :state where u.id = :id ")
  int updateUserState(  @Param("id") long id , @Param("state") int state);
  
  @Modifying
  @Transactional
  @Query("update Person u set u.last_Visit_date = :date where u.username = :name")
  int updateVisitDate(  @Param("date") Date visit_date,@Param("name") String name) ;
  
  @Query("select u.id, u.username,u.email,u.user_state,u.type,u.userInfo.photo , u.nbr_added , u.last_Visit_date from Person u  where u.id <> :id  ")
  List<Object[]> findAllUsers( @Param("id") long id);
}



