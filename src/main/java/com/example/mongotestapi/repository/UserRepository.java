package com.example.mongotestapi.repository;

import com.example.mongotestapi.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
