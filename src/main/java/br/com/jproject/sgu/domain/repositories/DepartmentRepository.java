package br.com.jproject.sgu.domain.repositories;

import br.com.jproject.sgu.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findDepartmentByName(String name);

}
