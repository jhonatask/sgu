package br.com.jproject.sgu.domain.service;

import br.com.jproject.sgu.application.dto.resquest.UserRequestDTO;
import br.com.jproject.sgu.domain.model.ArquivoCSV;
import br.com.jproject.sgu.domain.repositories.ArquivoCSVRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CsvService {

    private final ArquivoCSVRepository diretorioCSVRepository;
    private final UserService userService;

    public CsvService(ArquivoCSVRepository diretorioCSVRepository, UserService userService) {
        this.diretorioCSVRepository = diretorioCSVRepository;
        this.userService = userService;
    }

    public void importarTodosArquivos() throws IOException {
        Optional<ArquivoCSV> diretorio = diretorioCSVRepository.findById(UUID.fromString("c75d04ee-282a-4567-a92e-3bdc8af0d355")); // ID do diretório configurado

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
}
