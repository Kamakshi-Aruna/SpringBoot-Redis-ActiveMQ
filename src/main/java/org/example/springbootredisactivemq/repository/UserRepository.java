package org.example.springbootredisactivemq.repository;

import org.example.springbootredisactivemq.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
