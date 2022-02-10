package io.khaminfo.askmore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.khaminfo.askmore.domain.Chat;
@Repository
public interface ChatRepository  extends CrudRepository<Chat, Long>{
	@Query("select c.id, c.content,c.sender,c.receiver,c.sendAt,c.vue from Chat c where (c.sender= :userOne and c.receiver=:userTwo) or (c.sender= :userTwo and c.receiver=:userOne) ORDER BY c.id  ")
	  List<Object[]> getAllMessages( @Param("userOne") long userOne, @Param("userTwo") long userTwo);
	  @Modifying
	  @Transactional
	  @Query("update Chat c set c.vue = true where c.sender = :sender AND c.receiver = :receiver ")
	  int updateSeen( @Param("sender") long sender,    @Param("receiver") long receiver);
}
