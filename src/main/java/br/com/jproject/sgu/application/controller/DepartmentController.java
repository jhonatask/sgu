package br.com.jproject.sgu.application.controller;

import br.com.jproject.sgu.application.dto.response.DepartmentResponseDTO;
import br.com.jproject.sgu.application.dto.response.UserResponseDTO;
import br.com.jproject.sgu.application.dto.resquest.DepartmentRequestDTO;
import br.com.jproject.sgu.domain.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Cadastra um novo departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar um departamento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartament(@RequestBody DepartmentRequestDTO departmentRequestDTO){
        DepartmentResponseDTO department = departmentService.createDepartment(departmentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @Operation(summary = "Listar departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso ao listar os departamento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Error",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> listAllDepartment(){
        List<DepartmentResponseDTO> departmentList = departmentService.listAllDepartment();
        return ResponseEntity.ok(departmentList);
    }
}
