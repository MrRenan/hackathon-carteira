package br.com.fiap.features.carteira.adapter.in.rest.v1.controller;

import br.com.fiap.features.carteira.adapter.in.rest.v1.mapper.CarteiraInRestMapper;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.AtualizarVacinaAplicadaRequestStub;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.CriarCarteiraRequestStub;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.IncluirVacinaAplicadaRequestStub;
import br.com.fiap.features.carteira.adapter.in.rest.v1.response.CarteiraResponse;
import br.com.fiap.features.carteira.application.usecase.CarteiraUseCase;
import br.com.fiap.features.carteira.application.usecase.response.CarteiraUseCaseResponseStub;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cliente / Rest / V1 / Carteira Controller")
class CarteiraControllerTest {

    private static final String BASE_URL = "/carteira/v1";

    Faker faker = new Faker();

    @InjectMocks
    private CarteiraController controller;
    @Spy
    private CarteiraInRestMapper mapper = Mappers.getMapper(CarteiraInRestMapper.class);
    @Mock
    private CarteiraUseCase useCase;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Nested
    @DisplayName("API de criar carteira")
    class CriarCarteira {

        @Test
        @DisplayName("Deve executar API V1 de criar carteira com sucesso")
        void test01() throws Exception {
            // ASSETS
            var request = CriarCarteiraRequestStub.novo().build();
            var useCaseResponse = CarteiraUseCaseResponseStub.novo().build();
            given(useCase.executarCriarCarteira(any())).willReturn(useCaseResponse);
            // ACTION
            var result = mockMvc.perform(post(BASE_URL)
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));
            // ASSERTIONS
            result.andExpect(status().isCreated());
            var json = result.andReturn().getResponse().getContentAsString();
            var response = objectMapper.readValue(json, CarteiraResponse.class);
            assertThat(response).usingRecursiveComparison().isEqualTo(useCaseResponse);
            verify(mapper).paraCriarCarteiraUseCaseRequest(request);
            verify(mapper).paraCarteiraResponse(useCaseResponse);
        }

    }

    @Nested
    @DisplayName("API de buscar carteira por CPF")
    class BuscarCarteiraPorCPF {

        @Test
        @DisplayName("Deve executar API V1 de buscar carteira por cpf com sucesso")
        void test01() throws Exception {
            // ASSETS
            var cpf = faker.number().digits(11);
            var useCaseResponse = CarteiraUseCaseResponseStub.novo().build();
            given(useCase.executarBuscarCarteiraPorCpf(any())).willReturn(useCaseResponse);
            // ACTION
            var result = mockMvc.perform(get(BASE_URL.concat(String.format("/%s", cpf)))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));
            // ASSERTIONS
            result.andExpect(status().isOk());
            var json = result.andReturn().getResponse().getContentAsString();
            var response = objectMapper.readValue(json, CarteiraResponse.class);
            assertThat(response).usingRecursiveComparison().isEqualTo(useCaseResponse);
            verify(mapper).paraCarteiraResponse(useCaseResponse);
        }

    }

    @Nested
    @DisplayName("API de buscar todos os carteiras")
    class BuscarTodosCarteiras {

        @Test
        @DisplayName("Deve executar API V1 de buscar todos os carteiras com sucesso")
        void test01() throws Exception {
            // ASSETS
            var useCaseResponse = List.of(CarteiraUseCaseResponseStub.novo().build());
            given(useCase.executarListarTodasCarteiras()).willReturn(useCaseResponse);
            // ACTION
            var result = mockMvc.perform(get(BASE_URL)
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));
            // ASSERTIONS
            result.andExpect(status().isOk());
            var json = result.andReturn().getResponse().getContentAsString();
            var response = objectMapper.readValue(json, new TypeReference<List<CarteiraResponse>>() {
            });
            assertThat(response).usingRecursiveComparison().isEqualTo(useCaseResponse);
            verify(mapper, Mockito.times(useCaseResponse.size())).paraCarteiraResponse(any());
        }

    }

    @Nested
    @DisplayName("API de atualizar vacina aplicada")
    class AtualizarVacinaAplicada {

        @Test
        @DisplayName("Deve executar API V1 de atualizar vacina aplicada com sucesso")
        void test01() throws Exception {
            // ASSETS
            var cpf = faker.number().digits(11);
            var request = AtualizarVacinaAplicadaRequestStub.novo().build();
            var useCaseResponse = CarteiraUseCaseResponseStub.novo().build();
            given(useCase.executarAtualizarVacinaAplicada(any())).willReturn(useCaseResponse);
            // ACTION
            var result = mockMvc.perform(put(BASE_URL.concat(String.format("/%s/atualizar", cpf)))
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));
            // ASSERTIONS
            result.andExpect(status().isOk());
            var json = result.andReturn().getResponse().getContentAsString();
            var response = objectMapper.readValue(json, CarteiraResponse.class);
            assertThat(response).usingRecursiveComparison().isEqualTo(useCaseResponse);
            verify(mapper).paraAtualizarVacinaAplicadaUseCaseRequest(cpf, request);
            verify(mapper).paraCarteiraResponse(useCaseResponse);
        }

    }

    @Nested
    @DisplayName("API de incluir vacina aplicada")
    class IncluirVacinaAplicada {

        @Test
        @DisplayName("Deve executar API V1 de incluir vacina aplicada com sucesso")
        void test01() throws Exception {
            // ASSETS
            var cpf = faker.number().digits(11);
            var request = IncluirVacinaAplicadaRequestStub.novo().build();
            var useCaseResponse = CarteiraUseCaseResponseStub.novo().build();
            given(useCase.executarIncluirVacinaAplicada(any())).willReturn(useCaseResponse);
            // ACTION
            var result = mockMvc.perform(post(BASE_URL.concat(String.format("/%s/inserirVacina", cpf)))
                    .content(objectMapper.writeValueAsBytes(request))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));
            // ASSERTIONS
            result.andExpect(status().isOk());
            var json = result.andReturn().getResponse().getContentAsString();
            var response = objectMapper.readValue(json, CarteiraResponse.class);
            assertThat(response).usingRecursiveComparison().isEqualTo(useCaseResponse);
            verify(mapper).paraIncluirVacinaAplicadaUseCaseRequest(cpf, request);
            verify(mapper).paraCarteiraResponse(useCaseResponse);
        }

    }

    @Nested
    @DisplayName("API de remover carteira")
    class RemoverCarteira {

        @Test
        @DisplayName("Deve executar API V1 de remover carteira com sucesso")
        void test01() throws Exception {
            // ASSETS
            doNothing().when(useCase).executarRemoverCarteira(any());
            var cpf = faker.number().digits(11);
            // ACTION
            var result = mockMvc.perform(delete(BASE_URL.concat(String.format("/%s", cpf)))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON));
            // ASSERTIONS
            result.andExpect(status().isNoContent());
            verify(mapper).paraRemoverCarteiraUseCaseRequest(cpf);
        }

    }

}