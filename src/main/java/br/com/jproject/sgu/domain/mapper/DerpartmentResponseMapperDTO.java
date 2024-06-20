package br.com.jproject.sgu.domain.mapper;

import br.com.jproject.sgu.application.dto.response.DepartmentResponseDTO;
import br.com.jproject.sgu.domain.model.Department;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DerpartmentResponseMapperDTO {
    DepartmentResponseDTO departmentToDepartmentResponseDTO(Department entity);
    Department departmentResponseDTOToDepartment(DepartmentResponseDTO entity);
}
