package br.com.fiap.features.carteira.application.mapper;

import br.com.fiap.features.carteira.application.port.request.AtualizarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.request.CriarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.response.CarteiraPortResponse;
import br.com.fiap.features.carteira.application.usecase.request.AtualizarVacinaAplicadaUseCaseRequest;
import br.com.fiap.features.carteira.application.usecase.request.IncluirVacinaAplicadaUseCaseRequest;
import br.com.fiap.features.carteira.application.usecase.response.CarteiraUseCaseResponse;
import br.com.fiap.infra.feign.pacienteservice.model.PacienteClientResponse;
import br.com.fiap.infra.feign.vacinaservice.model.VacinaClientResponse;
import org.mapstruct.AnnotateWith;
import org.mapstruct.AnnotateWith.Element;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@AnnotateWith(value = Component.class, elements = @Element(strings = "carteiraApplicationMapper"))
@Mapper(componentModel = "spring")
public interface CarteiraApplicationMapper {


    @Mapping(target = "cpf", source = "paciente.cpf")
    @Mapping(target = "paciente.nomeCompleto", source = "paciente.nomeCompleto")
    @Mapping(target = "paciente.dataNascimento", source = "paciente.dataNascimento")
    @Mapping(target = "paciente.telefone", source = "paciente.telefone")
    @Mapping(target = "paciente.email", source = "paciente.email")
    CriarCarteiraPortRequest paraCriarCarteiraPortRequest(PacienteClientResponse paciente, List<VacinaClientResponse> vacinas);

    CarteiraUseCaseResponse paraCarteiraUseCaseResponse(CarteiraPortResponse response);

    AtualizarCarteiraPortRequest paraAtualizarCarteiraPortRequest(CarteiraPortResponse useCaseRequest);

    @Mapping(target = "nome", source = "vacinaEncontrada.nome")
    @Mapping(target = "doseAplicada", source = "requestVacina.doseAplicada")
    @Mapping(target = "doses", source = "vacinaEncontrada.doses")
    @Mapping(target = "fabricante", source = "vacinaEncontrada.fabricante")
    @Mapping(target = "descricao", source = "vacinaEncontrada.descricao")
    @Mapping(target = "intervaloEntreDosesEmDias", source = "vacinaEncontrada.intervaloEntreDosesEmDias")
    @Mapping(target = "grupo", source = "vacinaEncontrada.grupo")
    @Mapping(target = "dataAplicacao", source = "requestVacina.dataAplicacao")
    CarteiraPortResponse.VacinaPortResponse paraVacinaPortResponse(
            CarteiraPortResponse.VacinaPortResponse vacinaEncontrada,
            AtualizarVacinaAplicadaUseCaseRequest.VacinaUseCaseRequest requestVacina
    );

    @Mapping(target = "vacinas", source = "listaVacinas")
    @Mapping(target = "cpf", source = "request.cpf")
    @Mapping(target = "paciente", source = "carteira.paciente")
    CarteiraPortResponse paraCarteiraAtualizada(
            CarteiraPortResponse carteira,
            AtualizarVacinaAplicadaUseCaseRequest request,
            List<CarteiraPortResponse.VacinaPortResponse> listaVacinas
    );

    @Mapping(target = "nome", source = "vacina.nome")
    @Mapping(target = "doseAplicada", source = "requestVacina.doseAplicada")
    @Mapping(target = "doses", source = "vacina.doses")
    @Mapping(target = "fabricante", source = "vacina.fabricante")
    @Mapping(target = "descricao", source = "vacina.descricao")
    @Mapping(target = "intervaloEntreDosesEmDias", source = "vacina.intervaloEntreDosesEmDias")
    @Mapping(target = "grupo", source = "vacina.grupo")
    @Mapping(target = "dataAplicacao", source = "requestVacina.dataAplicacao")
    CarteiraPortResponse.VacinaPortResponse paraVacinaPortResponse(
            VacinaClientResponse vacina,
            IncluirVacinaAplicadaUseCaseRequest.VacinaUseCaseRequest requestVacina);

    @Mapping(target = "vacinas", source = "vacinas")
    @Mapping(target = "cpf", source = "carteira.cpf")
    @Mapping(target = "paciente", source = "carteira.paciente")
    CarteiraPortResponse paraCarteiraPortResponse(
            CarteiraPortResponse carteira,
            List<CarteiraPortResponse.VacinaPortResponse> vacinas);

}