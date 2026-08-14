package ru.codeportfolio.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.scheduler.dao.UserRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getUserEmail(Long userId) {
        return userRepository.findById(userId).orElseThrow().getEmail();
    }


}
