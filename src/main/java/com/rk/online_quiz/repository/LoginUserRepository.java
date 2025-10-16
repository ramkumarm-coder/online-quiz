package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginUserRepository  extends JpaRepository<LoginUser, Long> {

    Optional<LoginUser> findByUsername(String username);
}
