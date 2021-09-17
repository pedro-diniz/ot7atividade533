package br.com.zup.edu

import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import jakarta.inject.Singleton

@Factory // fabrica o bean do nosso client
class GrpcClientFactory {

    @Singleton // passo o channel no arg. do método, p/ o Micronaut tomar conta dele ao invés de instanciar manualmente
    fun serasaClientStub(
        @GrpcChannel(value = "serasa") channel: ManagedChannel // channel configurado no application.yml
    ) : SerasaServiceGrpc.SerasaServiceBlockingStub? {

        return SerasaServiceGrpc
            .newBlockingStub(channel)

    }

}