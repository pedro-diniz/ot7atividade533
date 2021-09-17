package br.com.zup.edu

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Controller
class SerasaController(val grpcClient: SerasaServiceGrpc.SerasaServiceBlockingStub) {

    @Post("/api/serasa/clientes/verificar-situacao")
    fun verificar(@NotBlank cpf: String): HttpResponse<Any> {

        val request = SituacaoDoClienteRequest.newBuilder()
            .setCpf(cpf)
            .build()

        val response = grpcClient.verificarSituacaoDoCliente(request)
        val situacao = response.toModel()

        return HttpResponse.ok(SituacaoNoSerasaResponse(cpf, situacao))
    }
}

// devia ter feito essa extension function na proposta de solução. Tive feito o when dentro do controller mesmo.
fun SituacaoDoClienteResponse.toModel() : Situacao {
    return when(situacao) {
        SituacaoDoClienteResponse.Situacao.REGULAR -> Situacao.REGULARIZADA
        SituacaoDoClienteResponse.Situacao.IRREGULAR -> Situacao.NAO_REGULARIZADA
        else -> Situacao.SEM_INFORMACOES
    }
}

data class SituacaoNoSerasaResponse(
    val cpf: String,
    val situacao: Situacao
)

enum class Situacao {
    SEM_INFORMACOES,
    REGULARIZADA,
    NAO_REGULARIZADA
}