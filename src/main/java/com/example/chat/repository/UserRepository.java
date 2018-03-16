package com.example.chat.repository;

import com.example.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author liuyiqian
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select username from User")
    List<String> findAllUsername();
}
