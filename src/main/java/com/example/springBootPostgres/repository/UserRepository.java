package com.example.springBootPostgres.repository;

import com.example.springBootPostgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
