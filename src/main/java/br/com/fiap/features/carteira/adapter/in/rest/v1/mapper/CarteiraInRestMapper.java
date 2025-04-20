package br.com.fiap.features.carteira.adapter.in.rest.v1.mapper;

import br.com.fiap.features.carteira.adapter.in.rest.v1.request.AtualizarVacinaAplicadaRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.CriarCarteiraRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.IncluirVacinaAplicadaRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.response.CarteiraResponse;
import br.com.fiap.features.carteira.application.usecase.request.*;
import br.com.fiap.features.carteira.application.usecase.response.CarteiraUseCaseResponse;
import org.mapstruct.AnnotateWith;
import org.mapstruct.AnnotateWith.Element;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@AnnotateWith(value = Component.class, elements = @Element(strings = "carteiraInRestMapper"))
@Mapper(componentModel = "spring")
public interface CarteiraInRestMapper {

    CriarCarteiraUseCaseRequest paraCriarCarteiraUseCaseRequest(CriarCarteiraRequest request);

    CarteiraResponse paraCarteiraResponse(CarteiraUseCaseResponse useCaseResponse);

    @Mapping(target = "vacina.nome", source = "vacina.nome")
    @Mapping(target = "vacina.doseAplicada", source = "vacina.doseAplicada")
    @Mapping(target = "vacina.dataAplicacao", source = "vacina.dataAplicacao")
    AtualizarVacinaAplicadaUseCaseRequest paraAtualizarVacinaAplicadaUseCaseRequest(String cpf, AtualizarVacinaAplicadaRequest vacina);

    RemoverCarteiraUseCaseRequest paraRemoverCarteiraUseCaseRequest(String cpf);

    @Mapping(target = "vacina.nome", source = "vacina.nome")
    @Mapping(target = "vacina.doseAplicada", source = "vacina.doseAplicada")
    @Mapping(target = "vacina.dataAplicacao", source = "vacina.dataAplicacao")
    IncluirVacinaAplicadaUseCaseRequest paraIncluirVacinaAplicadaUseCaseRequest(String cpf, IncluirVacinaAplicadaRequest vacina);
}