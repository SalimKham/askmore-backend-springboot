package io.khaminfo.askmore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.khaminfo.askmore.domain.Comment;

@Repository
public interface CommentRespository extends CrudRepository<Comment, Long> {

}
