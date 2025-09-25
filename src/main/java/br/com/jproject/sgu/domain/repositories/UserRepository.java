package br.com.jproject.sgu.domain.repositories;

import br.com.jproject.sgu.domain.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    @Cacheable(value = "user-by-email", key = "#email")
    @Query("SELECT u FROM User u WHERE u.email.value = :email")
    Optional<User> findByEmail(@Param("email") String email);
    
    @Cacheable(value = "user-by-cpf", key = "#cpfOrCnpj")
    Optional<User> findBycpforcnpj(String cpfOrCnpj);
    
    Optional<User> findByName(String name);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT u FROM User u JOIN FETCH u.department WHERE u.id = :id")
    Optional<User> findByIdWithDepartment(@Param("id") UUID id);
    
    @Query("SELECT u FROM User u WHERE u.department.id = :departmentId")
    Page<User> findByDepartmentId(@Param("departmentId") UUID departmentId, Pageable pageable);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.department.id = :departmentId")
    long countByDepartmentId(@Param("departmentId") UUID departmentId);
    
    @Query("SELECT u FROM User u WHERE u.datacadastro >= :startDate AND u.datacadastro <= :endDate")
    Page<User> findByDateRange(@Param("startDate") java.time.LocalDateTime startDate, 
                              @Param("endDate") java.time.LocalDateTime endDate, 
                              Pageable pageable);
}
