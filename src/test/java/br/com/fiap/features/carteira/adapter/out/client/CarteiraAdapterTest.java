package br.com.fiap.features.carteira.adapter.out.client;

import br.com.fiap.features.carteira.adapter.out.client.mapper.CarteiraOutMapper;
import br.com.fiap.features.carteira.application.port.request.AtualizarCarteiraPortRequestStub;
import br.com.fiap.features.carteira.application.port.request.CriarCarteiraPortRequestStub;
import br.com.fiap.features.carteira.domain.exception.CarteiraCadastradaException;
import br.com.fiap.features.carteira.domain.exception.CarteiraNaoEncontradaException;
import br.com.fiap.infra.mongodb.carteira.document.CarteiraDocument;
import br.com.fiap.infra.mongodb.carteira.document.CarteiraDocumentStub;
import br.com.fiap.infra.mongodb.carteira.repository.CarteiraMongoDBRepository;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarteiraAdapterTest {

    private static final String CPF = "12345678901";
    @InjectMocks
    private CarteiraAdapter adapter;
    @Mock
    private CarteiraMongoDBRepository repository;
    @Mock
    private MongoTemplate mongoTemplate;
    @Spy
    private CarteiraOutMapper mapper = getMapper(CarteiraOutMapper.class);

    @Nested
    @DisplayName("Porta de criar carteira")
    class CriarCarteira {

        @Test
        @DisplayName("Deve executar porta de criar carteira com sucesso")
        void test01() {
            // ASSETS
            var request = CriarCarteiraPortRequestStub.novo().build();
            var document = CarteiraDocumentStub.novo().build();
            when(repository.findByCpf(any())).thenReturn(Optional.empty());
            when(repository.save(any())).thenReturn(document);

            // ACTION
            var result = adapter.criarCarteira(request);

            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(document);
            verify(mapper).paraCarteiraDocument(request);
            verify(mapper).paraCarteiraPortResponse(document);

        }

        @Test
        @DisplayName("Deve executar porta de criar carteira com erro, quando carteira ja existir")
        void test02() {
            // ASSETS
            var request = CriarCarteiraPortRequestStub.novo().build();
            var document = CarteiraDocumentStub.novo().build();
            when(repository.findByCpf(any())).thenReturn(Optional.of(document));

            // ACTION & ASSERTIONS
            assertThrows(CarteiraCadastradaException.class, () -> adapter.criarCarteira(request));
            verify(mapper).paraCarteiraDocument(request);

        }

    }

    @Nested
    @DisplayName("Porta de buscar carteira por CPF")
    class BuscarCarteiraPorCpf {

        @Test
        @DisplayName("Deve executar porta de buscar carteira por CPF com sucesso")
        void test01() {
            // ASSETS
            var document = CarteiraDocumentStub.novo().build();
            when(repository.findByCpf(any())).thenReturn(Optional.of(document));

            // ACTION
            var result = adapter.buscarCarteiraPorCpf(CPF);

            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(document);
            verify(mapper).paraCarteiraPortResponse(document);

        }

        @Test
        @DisplayName("Deve executar porta de criar carteira com erro, quando carteira ja existir")
        void test02() {
            // ASSETS
            when(repository.findByCpf(any())).thenReturn(Optional.empty());

            // ACTION & ASSERTIONS
            assertThrows(CarteiraNaoEncontradaException.class, () -> adapter.buscarCarteiraPorCpf(CPF));

        }

    }

    @Nested
    @DisplayName("Porta de listar todos carteiras")
    class ListarTodosCarteiras {

        @Test
        @DisplayName("Deve executar porta de listar todos carteiras com sucesso")
        void test01() {
            // ASSETS
            var document = List.of(CarteiraDocumentStub.novo().build());
            when(repository.findAll()).thenReturn(document);

            // ACTION
            var result = adapter.listarTodasCarteiras();

            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(document);
            verify(mapper, times(document.size())).paraCarteiraPortResponse(any());

        }

    }

    @Nested
    @DisplayName("Porta de atualizar carteira")
    class AtualizarCarteira {

        @Test
        @DisplayName("Deve executar porta de atualizar carteira com sucesso")
        void test01() {
            // ASSETS
            var request = AtualizarCarteiraPortRequestStub.novo().build();
            var document = CarteiraDocument.builder()
                    .cpf(CPF)
                    .vacinas(List.of(CarteiraDocument.VacinaDocument.builder().build()))
                    .paciente(CarteiraDocument.PacienteDocument.builder().build())
                    .build();
            when(mongoTemplate.findAndModify(
                    any(Query.class),
                    any(Update.class),
                    any(FindAndModifyOptions.class),
                    eq(CarteiraDocument.class)
            )).thenReturn(document);

            // ACTION
            var result = adapter.atualizarCarteira(request);

            // ASSERTIONS
            assertThat(result).usingRecursiveComparison().isEqualTo(document);
            verify(mapper).paraCarteiraDocument(request);
            verify(mapper).paraCarteiraPortResponse(document);
            verify(mapper).paraUpdate(any());

        }

        @Test
        @DisplayName("Deve executar porta de atualizar carteira com erro, quando carteira não for encontrado")
        void test02() {
            // ASSETS
            var request = AtualizarCarteiraPortRequestStub.novo().build();
            when(mongoTemplate.findAndModify(
                    any(Query.class),
                    any(Update.class),
                    any(FindAndModifyOptions.class),
                    eq(CarteiraDocument.class)
            )).thenReturn(null);

            // ACTION & ASSERTIONS
            assertThrows(CarteiraNaoEncontradaException.class, () -> adapter.atualizarCarteira(request));

        }

    }

    @Nested
    @DisplayName("Porta de remover carteira")
    class RemoverCarteira {

        @Test
        @DisplayName("Deve executar porta de remover carteira com sucesso")
        void test01() {
            // ASSETS
            when(mongoTemplate.remove(
                    any(),
                    eq(CarteiraDocument.class)
            )).thenReturn(DeleteResult.acknowledged(1L));

            // ACTION
            Assertions.assertDoesNotThrow(() -> adapter.removerCarteira(CPF));

            // ASSERTIONS
            verify(mongoTemplate).remove(
                    Query.query(Criteria.where("cpf").is(CPF)),
                    CarteiraDocument.class
            );

        }

        @Test
        @DisplayName("Deve executar porta de remover carteira com erro, quando carteira não for encontrado")
        void test02() {
            // ASSETS
            when(mongoTemplate.remove(
                    any(Query.class),
                    any(Class.class)
            )).thenReturn(DeleteResult.acknowledged(0L));

            // ACTION & ASSERTIONS
            assertThrows(CarteiraNaoEncontradaException.class,
                    () -> adapter.removerCarteira(CPF));

        }

    }

}