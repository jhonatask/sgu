package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.domain.mapper.UserResponseMapperDTO;
import br.com.jproject.sgu.domain.model.User;
import br.com.jproject.sgu.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserResponseMapperDTO userResponseMapperDTO;

    @Cacheable(value = "users", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<UserResponseDTO> findAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userResponseMapperDTO::userToUserResponseDTO);
    }

    @Cacheable(value = "user-by-id", key = "#id")
    public Optional<UserResponseDTO> findById(UUID id) {
        return userRepository.findByIdWithDepartment(id)
                .map(userResponseMapperDTO::userToUserResponseDTO);
    }

    @Cacheable(value = "user-by-email", key = "#email")
    public Optional<UserResponseDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userResponseMapperDTO::userToUserResponseDTO);
    }

    @Cacheable(value = "user-by-cpf", key = "#cpfOrCnpj")
    public Optional<UserResponseDTO> findByCpfOrCnpj(String cpfOrCnpj) {
        return userRepository.findBycpforcnpj(cpfOrCnpj)
                .map(userResponseMapperDTO::userToUserResponseDTO);
    }

    @Cacheable(value = "users-by-name", key = "#name + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<UserResponseDTO> findByNameContaining(String name, Pageable pageable) {
        Page<User> users = userRepository.findByNameContainingIgnoreCase(name, pageable);
        return users.map(userResponseMapperDTO::userToUserResponseDTO);
    }

    @Cacheable(value = "users-by-department", key = "#departmentId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<UserResponseDTO> findByDepartmentId(UUID departmentId, Pageable pageable) {
        Page<User> users = userRepository.findByDepartmentId(departmentId, pageable);
        return users.map(userResponseMapperDTO::userToUserResponseDTO);
    }

    @Cacheable(value = "users-by-date-range", key = "#startDate + '-' + #endDate + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<UserResponseDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<User> users = userRepository.findByDateRange(startDate, endDate, pageable);
        return users.map(userResponseMapperDTO::userToUserResponseDTO);
    }

    public long countByDepartmentId(UUID departmentId) {
        return userRepository.countByDepartmentId(departmentId);
    }

    @CacheEvict(value = {"users", "user-by-id", "user-by-email", "user-by-cpf", "users-by-name", "users-by-department", "users-by-date-range"}, allEntries = true)
    public void evictCache() {
        // Método para limpar cache quando necessário
    }
}
