package br.com.jproject.sgu;

import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.domain.model.ArquivoCSV;
import br.com.jproject.sgu.domain.repositories.ArquivoCSVRepository;
import br.com.jproject.sgu.domain.service.CsvService;
import br.com.jproject.sgu.domain.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CsvServiceTest {

    @Mock
    private ArquivoCSVRepository arquivoCSVRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private CsvService csvService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        csvService = new CsvService(arquivoCSVRepository, userService);
    }

    @TempDir
    Path tempDir;

    @Test
    void deveLancarExcecaoQuandoDiretorioNaoExisteNoBanco() {
        when(arquivoCSVRepository.findById(any())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> csvService.importarTodosArquivos());
        assertEquals("Nenhum diretório CSV configurado.", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoDiretorioFisicoNaoExiste() {
        ArquivoCSV arquivoCSV = new ArquivoCSV();
        arquivoCSV.setCaminho(tempDir.resolve("nao_existe").toString());
        when(arquivoCSVRepository.findById(any())).thenReturn(Optional.of(arquivoCSV));
        assertThrows(FileNotFoundException.class, () -> csvService.importarTodosArquivos());
    }

    @Test
    void deveIgnorarArquivosNaoCSV() throws IOException {
        ArquivoCSV arquivoCSV = new ArquivoCSV();
        arquivoCSV.setCaminho(tempDir.toString());
        when(arquivoCSVRepository.findById(any())).thenReturn(Optional.of(arquivoCSV));

        // Cria um arquivo .txt
        Files.createFile(tempDir.resolve("arquivo.csv"));

        csvService.importarTodosArquivos();

        verify(userService, never()).createUser(any());
    }

    @Test
    void deveImportarArquivoCSVValido() throws IOException {
        ArquivoCSV arquivoCSV = new ArquivoCSV();
        arquivoCSV.setCaminho(tempDir.toString());
        when(arquivoCSVRepository.findById(any())).thenReturn(Optional.of(arquivoCSV));

        Path csv = tempDir.resolve("usuarios.csv");
        String conteudo = "cpf,nome,email,senha,departamento,telefone\n" +
                "12345678900,Joao,joao@email.com,senha,00000000-0000-0000-0000-000000000001,999999999\n";
        Files.write(csv, conteudo.getBytes());

        csvService.importarTodosArquivos();

        verify(userService, times(1)).createUser(any(UserRequestDTO.class));
    }

    @Test
    void deveIgnorarLinhasInvalidasNoCSV() throws IOException {
        ArquivoCSV arquivoCSV = new ArquivoCSV();
        arquivoCSV.setCaminho(tempDir.toString());
        when(arquivoCSVRepository.findById(any())).thenReturn(Optional.of(arquivoCSV));

        Path csv = tempDir.resolve("usuarios.csv");
        String conteudo = "cpf,nome,email,senha,departamento,telefone\n" +
                "apenas,dois,campos\n";
        Files.write(csv, conteudo.getBytes());

        csvService.importarTodosArquivos();

        verify(userService, never()).createUser(any());
    }

    @Test
    void deveIgnorarCSVVazio() throws IOException {
        ArquivoCSV arquivoCSV = new ArquivoCSV();
        arquivoCSV.setCaminho(tempDir.toString());
        when(arquivoCSVRepository.findById(any())).thenReturn(Optional.of(arquivoCSV));

        Path csv = tempDir.resolve("usuarios.csv");
        Files.write(csv, "".getBytes());

        csvService.importarTodosArquivos();

        verify(userService, never()).createUser(any());
    }
}
