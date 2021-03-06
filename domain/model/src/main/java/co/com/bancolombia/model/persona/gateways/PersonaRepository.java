package co.com.bancolombia.model.persona.gateways;

import co.com.bancolombia.model.persona.Person;
import reactor.core.publisher.Mono;

public interface PersonaRepository {
    Mono<Void> encolarPersona(Person persona);
}
