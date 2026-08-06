package ru.codeportfolio.sсheduler.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.codeportfolio.sсheduler.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUsersByEmail(String email);
}
