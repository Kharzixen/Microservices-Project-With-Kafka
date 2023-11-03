package com.kharzixen.userservice.repository;

import com.kharzixen.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
