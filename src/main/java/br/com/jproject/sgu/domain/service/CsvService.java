package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.domain.model.ArquivoCSV;
import br.com.jproject.sgu.domain.repositories.ArquivoCSVRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvService {

    @Value("${csv.diretorio.id}")
    private String diretorioId;

    private final ArquivoCSVRepository diretorioCSVRepository;
    private final UserService userService;

    public CsvService(ArquivoCSVRepository diretorioCSVRepository, UserService userService) {
        this.diretorioCSVRepository = diretorioCSVRepository;
        this.userService = userService;
    }

    public void importarTodosArquivos() throws IOException {
        Optional<ArquivoCSV> diretorio = diretorioCSVRepository.findById(UUID.fromString(diretorioId)); // ID do diretório configurado

        if (diretorio.isEmpty()) {
            throw new RuntimeException("Nenhum diretório CSV configurado.");
        }

        Path pasta = Paths.get(diretorio.get().getCaminho());

        if (!Files.exists(pasta)) {
            throw new FileNotFoundException("Diretório não encontrado: " + pasta);
        }

        try (Stream<Path> arquivos = Files.list(pasta)) {
            arquivos.filter(path -> path.toString().endsWith(".csv"))
                    .forEach(this::importarArquivo);
        }
    }

    private void importarArquivo(Path arquivoCSV) {
        try (BufferedReader reader = Files.newBufferedReader(arquivoCSV)) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readLine()) != null) {

                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                // Ignora linhas vazias ou com menos de 6 campos
                String[] campos = linha.split(",");
                if (campos.length < 6) continue;

                userService.createUser(
                        UserRequestDTO.builder()
                                .cpforcnpj(campos[0])
                                .name(campos[1])
                                .email(campos[2])
                                .password(campos[3])
                                .department(UUID.fromString(campos[4]))
                                .telefone(campos[5])
                                .build()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importarArquivoUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode estar vazio");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Arquivo deve ser do tipo CSV");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linha;
            boolean primeiraLinha = true;
            int linhaNumero = 0;
            
            while ((linha = reader.readLine()) != null) {
                linhaNumero++;
                
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                // Ignora linhas vazias ou com menos de 6 campos
                String[] campos = linha.split(",");
                if (campos.length < 6) {
                    System.out.println("Linha " + linhaNumero + " ignorada: campos insuficientes");
                    continue;
                }

                try {
                    userService.createUser(
                            UserRequestDTO.builder()
                                    .cpforcnpj(campos[0])
                                    .name(campos[1])
                                    .email(campos[2])
                                    .password(campos[3])
                                    .department(UUID.fromString(campos[4]))
                                    .telefone(campos[5])
                                    .build()
                    );
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + linhaNumero + ": " + e.getMessage());
                    // Continua processando outras linhas mesmo se uma falhar
                }
            }
        }
    }
}
