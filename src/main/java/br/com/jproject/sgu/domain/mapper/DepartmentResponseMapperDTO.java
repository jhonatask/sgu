package br.com.jproject.sgu.domain.mapper;

import br.com.jproject.sgu.application.dto.response.DepartmentResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.DepartmentRequestDTO;
import br.com.jproject.sgu.domain.model.Department;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DepartmentResponseMapperDTO {
    DepartmentResponseDTO departmentToDepartmentResponseDTO(Department entity);
    Department departmentResponseDTOToDepartment(DepartmentResponseDTO entity);
    DepartmentRequestDTO  departmentToDepartmentRequestDTO(Department entity);
    Department departmentRequestDTOToDepartment(DepartmentRequestDTO entity);
}
