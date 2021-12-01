package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.PersonDto;
import co.com.bancolombia.api.dto.ResponseDto;
import co.com.bancolombia.model.persona.Persona;
import co.com.bancolombia.model.persona.Response;
import co.com.bancolombia.usecase.persona.PersonaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log
public class Handler {
//private  final UseCase useCase;
//private  final UseCase2 useCase2;
    private final PersonaUseCase personaUseCase;
    private final ObjectMapper mapper;

    public Mono<ServerResponse> handlerPersona(ServerRequest serverRequest) {
        // usecase.logic();
        return Mono.just(serverRequest)
                .flatMap(serverRequest1 -> serverRequest1.bodyToMono(PersonDto.class))
                .map(personDto ->  mapper.map(personDto, Persona.class))
                .doOnNext(persona -> log.info("Persona: " + persona))
                .flatMap(personaUseCase::encolarMensaje)
                 .flatMap(this::getServerResponseMono)
                ;
    }

    private Mono<ServerResponse> getServerResponseMono(Response response) {
        return ServerResponse
                .ok()
                .body(Mono.just(response), ResponseDto.class);
    }
}
