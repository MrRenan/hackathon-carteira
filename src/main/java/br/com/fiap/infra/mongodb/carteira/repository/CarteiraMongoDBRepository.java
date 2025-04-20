package br.com.fiap.infra.mongodb.carteira.repository;

import br.com.fiap.infra.mongodb.carteira.document.CarteiraDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraMongoDBRepository extends MongoRepository<CarteiraDocument, String> {

    Optional<CarteiraDocument> findByCpf(String cpf);

}