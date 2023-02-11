package co.com.bancolombia.usecase.parametrization;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class ParametrizationUseCase {
    public Mono<String> execute(String service, String catolg) {
        log.info("Service: ".concat(service).concat(" Catalog: ").concat(catolg));
        return Mono.empty();
    }

}
