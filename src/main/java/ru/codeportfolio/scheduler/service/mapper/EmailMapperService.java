package ru.codeportfolio.scheduler.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codeportfolio.scheduler.dao.UserRepository;
import ru.codeportfolio.scheduler.dto.EmailDto;

@Transactional
@Service
public class EmailMapperService {

    private final UserRepository userRepository;

    public EmailMapperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public EmailDto getEmailDto(Long userId, String text, String header) {
        return new EmailDto(
                userRepository.findById(userId).orElseThrow().getEmail(),
                header,
                text
        );
    }
}
