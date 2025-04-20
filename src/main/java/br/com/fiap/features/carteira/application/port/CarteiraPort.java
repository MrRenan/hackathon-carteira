package br.com.fiap.features.carteira.application.port;

import br.com.fiap.features.carteira.application.port.request.AtualizarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.request.CriarCarteiraPortRequest;
import br.com.fiap.features.carteira.application.port.response.CarteiraPortResponse;

import java.util.List;

public interface CarteiraPort {

    CarteiraPortResponse criarCarteira(CriarCarteiraPortRequest request);

    CarteiraPortResponse buscarCarteiraPorCpf(String cpf);

    List<CarteiraPortResponse> listarTodasCarteiras();

    CarteiraPortResponse atualizarCarteira(AtualizarCarteiraPortRequest portRequest);

    void removerCarteira(String cpf);
}
