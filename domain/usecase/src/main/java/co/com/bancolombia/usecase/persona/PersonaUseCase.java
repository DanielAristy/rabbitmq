package co.com.bancolombia.usecase.persona;

import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.Response;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Log
public class PersonaUseCase {

    private final PersonaRepository personaRepository;

    public Mono<Response> encolarMensaje(Persona persona){
        log.info(":: Execute PersonaUseCase encolarMensaje ::");
        return Mono.just(persona)
                .doOnNext(persona1 -> log.info("Llegada desde handler: " + persona1))
                .flatMap(persona1 -> personaRepository.encolarPersona(persona1.toBuilder().build()))
                .doOnNext(unused -> log.info(unused.toString()))
                .flatMap(unused -> Mono.just(
                        Response
                                .builder()
                                .code("200")
                                .message("Se envio correctamente!")
                                .eventId("1dfsf246154fdsfd8f42")
                                .build())
                )
                .doOnNext(response -> log.info("Response caso de uso: " + response))
                .doOnNext(response -> log.info(":: FIN PersonaUseCase ::"));

    }
}
