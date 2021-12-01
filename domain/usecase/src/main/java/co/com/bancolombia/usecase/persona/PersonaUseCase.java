package co.com.bancolombia.usecase.persona;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Log
public class PersonaUseCase {

    public Mono<Response> encolarMensaje(Persona persona){
        log.info(":: Execute PersonaUseCase encolarMensaje ::");
        return Mono.just(persona)
                .flatMap(persona1 -> Mono.just(
                        Response
                                .builder()
                                .code("200")
                                .message("Se envio correctamente!")
                                .eventId("1dfsf246154fdsfd8f42")
                                .build())
                )
                .doOnNext(response -> log.info(":: FIN PersonaUseCase ::"));

    }
}
