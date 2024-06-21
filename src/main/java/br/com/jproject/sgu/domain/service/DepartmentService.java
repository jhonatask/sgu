package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.core.exceptions.exception.DepartmentNotFoundException;
import br.com.jproject.sgu.domain.model.Department;
import br.com.jproject.sgu.domain.repositories.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department getDepartment(UUID id) {
        return departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
    }
}
