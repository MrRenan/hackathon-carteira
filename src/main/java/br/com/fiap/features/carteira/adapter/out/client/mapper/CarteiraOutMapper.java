package br.com.fiap.features.carteira.adapter.out.client.mapper;

import br.com.fiap.features.carteira.application.port.request.AtualizarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.request.CriarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.response.CarteiraPortResponse;
import br.com.fiap.infra.mongodb.carteira.document.CarteiraDocument;
import org.mapstruct.AnnotateWith;
import org.mapstruct.AnnotateWith.Element;
import org.mapstruct.Mapper;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@AnnotateWith(value = Component.class, elements = @Element(strings = "carteiraOutMapper"))
@Mapper(componentModel = "spring")
public interface CarteiraOutMapper {

    CarteiraDocument paraCarteiraDocument(CriarCarteiraPortRequest request);

    CarteiraDocument paraCarteiraDocument(AtualizarCarteiraPortRequest request);

    CarteiraPortResponse paraCarteiraPortResponse(CarteiraDocument document);

    default Update paraUpdate(CarteiraDocument document) {
        return new Update()
                .set("cpf", document.cpf())
                .set("paciente", document.paciente())
                .set("vacinas", document.vacinas());
    }

}
