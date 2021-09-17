package br.com.zup.edu

import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class SerasaGrpcServer : SerasaServiceGrpc.SerasaServiceImplBase() {

    private val logger = LoggerFactory.getLogger(SerasaServiceGrpc::class.java)

    override fun verificarSituacaoDoCliente(
        request: SituacaoDoClienteRequest?,
        responseObserver: StreamObserver<SituacaoDoClienteResponse>?
    ) {
        //
        logger.info("Analisando o cpf ${request!!.cpf}")

        val response = SituacaoDoClienteResponse.newBuilder()
            .setSituacao(geraSituacao(request.cpf))
            .build()

        logger.info("CPF ${request.cpf}: ${response.situacao}")

        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }

}

fun geraSituacao(cpf: String) : SituacaoDoClienteResponse.Situacao {
    if (cpf.toCharArray()[0].toInt() % 2 == 0) {
        return SituacaoDoClienteResponse.Situacao.IRREGULAR
    }
    return SituacaoDoClienteResponse.Situacao.REGULAR
}