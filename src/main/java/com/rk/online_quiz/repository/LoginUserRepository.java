package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginUserRepository  extends JpaRepository<LoginUser, Long> {

    Optional<LoginUser> findByUsername(String username);
}
