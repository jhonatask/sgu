package br.com.jproject.sgu.application.usecase;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repository.UserRepository;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUsersUseCase {

    private final UserRepository userRepository;
    private final UserResponseMapperDTO userResponseMapperDTO;

    @Cacheable(value = "users", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<UserResponseDTO> execute(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userResponseMapperDTO::userToUserResponseDTO);
    }
}
