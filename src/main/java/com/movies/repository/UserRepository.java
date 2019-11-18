package com.movies.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movies.model.UserInfo;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long>{

}
