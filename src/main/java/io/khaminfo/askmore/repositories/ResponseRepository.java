package io.khaminfo.askmore.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.khaminfo.askmore.domain.Response;
@Repository
public interface ResponseRepository extends CrudRepository<Response, Long>{

}
