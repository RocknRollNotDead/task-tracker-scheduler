package ru.codeportfolio.scheduler.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.scheduler.dao.UserRepository;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername(Long id) {
        return userRepository.findById(id).orElseThrow().getUsername();
    }

    public String getUserEmail(Long userId) {
        return userRepository.findById(userId).orElseThrow().getEmail();
    }


}
