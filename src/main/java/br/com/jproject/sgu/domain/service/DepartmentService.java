package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.application.dto.response.DepartmentResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.DepartmentRequestDTO;
import br.com.jproject.sgu.core.exceptions.exception.DepartmentNotFoundException;
import br.com.jproject.sgu.domain.mapper.DepartmentResponseMapperDTO;
import br.com.jproject.sgu.domain.model.Department;
import br.com.jproject.sgu.domain.repositories.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentResponseMapperDTO departmentResponseMapperDTO;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentResponseMapperDTO departmentResponseMapperDTO) {
        this.departmentRepository = departmentRepository;
        this.departmentResponseMapperDTO = departmentResponseMapperDTO;
    }

    public Department getDepartment(UUID id) {
        return departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
    }

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO) {
        Department newDepartment = departmentResponseMapperDTO.departmentRequestDTOToDepartment(departmentRequestDTO);
        newDepartment.setName(departmentRequestDTO.name);
        departmentRepository.save(newDepartment);
        return departmentResponseMapperDTO.departmentToDepartmentResponseDTO(newDepartment);
    }

    public List<DepartmentResponseDTO> listAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDTO> list = departments.stream().map(departmentResponseMapperDTO::departmentToDepartmentResponseDTO).toList();
        return list;
    }
}
