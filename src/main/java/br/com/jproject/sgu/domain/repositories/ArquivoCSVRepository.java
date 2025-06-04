package br.com.jproject.sgu.domain.repositories;

import br.com.jproject.sgu.domain.model.ArquivoCSV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArquivoCSVRepository  extends JpaRepository<ArquivoCSV, UUID> {
}
