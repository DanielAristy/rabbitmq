package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.PersonaDto;
import co.com.bancolombia.api.dto.ResponseDto;
import co.com.bancolombia.model.persona.Person;
import co.com.bancolombia.model.persona.Response;
import co.com.bancolombia.usecase.parametrization.ParametrizationUseCase;
import co.com.bancolombia.usecase.persona.PersonaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log
public class Handler {

    private final PersonaUseCase personaUseCase;
    private final ParametrizationUseCase parametrizationUseCase;
    private final ObjectMapper mapper;

    public Mono<ServerResponse> handlerPersona(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(serverRequest1 -> serverRequest1.bodyToMono(PersonaDto.class))
                .map(personaDto ->  mapper.mapBuilder(personaDto, Person.class).toBuilder()
                        .name(personaDto.getNombre())
                        .lastname(personaDto.getApellido())
                        .build()
                )
                .doOnNext(persona -> log.info("Persona: " + persona))
                .flatMap(personaUseCase::encolarMensaje)
                 .flatMap(this::getServerResponseMono);
    }

    private Mono<ServerResponse> getServerResponseMono(Response response) {
        return ServerResponse
                .ok()
                .body(Mono.just(response), ResponseDto.class);
    }

    public Mono<ServerResponse> handlerParametrization(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .filter(serverRequest1 -> serverRequest1.queryParam("id").isPresent())
                .filter(serverRequest1 -> serverRequest1.queryParam("role").isPresent())
                .flatMap(serverRequest1 -> parametrizationUseCase.execute(
                        serverRequest1.queryParam("id").get(),
                        serverRequest1.queryParam("role").get())
                )
                .then(ServerResponse.ok().body(Mono.just("Hola mundo"), String.class));
    }
}
