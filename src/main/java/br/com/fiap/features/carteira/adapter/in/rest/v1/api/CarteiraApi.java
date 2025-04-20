package br.com.fiap.features.carteira.adapter.in.rest.v1.api;

import br.com.fiap.features.carteira.adapter.in.rest.v1.Api;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.AtualizarVacinaAplicadaRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.CriarCarteiraRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.request.IncluirVacinaAplicadaRequest;
import br.com.fiap.features.carteira.adapter.in.rest.v1.response.CarteiraResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Validated
@RequestMapping(path = "/carteira/v1")
public interface CarteiraApi extends Api {

    @Operation(summary = "Criar carteira de vacinação")
    @PostMapping
    @ResponseStatus(CREATED)
    CarteiraResponse criarCarteira(@RequestBody CriarCarteiraRequest request);

    @Operation(summary = "Buscar cartaira por CPF")
    @GetMapping(path = "/{cpf}")
    @ResponseStatus(OK)
    CarteiraResponse buscarCarteiraPorCpf(@PathVariable("cpf") String cpf);

    @Operation(summary = "Listar todas as carteiras")
    @GetMapping
    @ResponseStatus(OK)
    List<CarteiraResponse> listarTodasCarteiras();

    @Operation(summary = "Atualizar uma vacina aplicada")
    @PutMapping(path = "/{cpf}/atualizar")
    @ResponseStatus(OK)
    CarteiraResponse atualizarVacinaAplicada(@PathVariable("cpf") String cpf,
                                             @RequestBody AtualizarVacinaAplicadaRequest request);

    @Operation(summary = "Inserir uma vacina na carteira")
    @PostMapping("/{cpf}/inserirVacina")
    CarteiraResponse incluirVacinaAplicada(@PathVariable("cpf") String cpf,
                                           @RequestBody IncluirVacinaAplicadaRequest request);

    @Operation(summary = "Remover carteira")
    @DeleteMapping(path = "/{cpf}")
    @ResponseStatus(NO_CONTENT)
    void removerCarteira(@PathVariable("cpf") String cpf);
}