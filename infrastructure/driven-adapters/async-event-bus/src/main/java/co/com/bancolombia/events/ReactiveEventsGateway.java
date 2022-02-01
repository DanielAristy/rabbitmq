package co.com.bancolombia.events;

import co.com.bancolombia.model.persona.Person;
import co.com.bancolombia.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Level;

import static reactor.core.publisher.Mono.from;

@Log
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements PersonaRepository {
    public static final String PERSONA_ROUTING_KEY = "create.person";
    private final DomainEventBus domainEventBus;

    @Override
    public Mono<Void> encolarPersona(Person persona) {
        log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{PERSONA_ROUTING_KEY, persona.toString()});
        return from(domainEventBus.emit(new DomainEvent<>(PERSONA_ROUTING_KEY, UUID.randomUUID().toString(), persona)));
    }
}
