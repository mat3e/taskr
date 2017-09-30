package io.github.mat3e.jhipster.taskr.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import io.github.mat3e.jhipster.taskr.domain.Worker;
import io.github.mat3e.jhipster.taskr.repository.WorkerRepository;

@Service
public class AuthorityMessagingService {
    private final Logger log = LoggerFactory.getLogger(AuthorityMessagingService.class);

    private MessageChannel producerChannel;
    private final WorkerRepository workerRepository;

    public AuthorityMessagingService(WorkerRepository workerRepository, Source source) {
        this.workerRepository = workerRepository;
        this.producerChannel = source.output();
    }

    @StreamListener(value = Sink.INPUT)
    public void consume(CheckValidityMessage msg) {
        String context = msg.getContext();

        log.debug("Received message for context: {}.", context);

        String requester = msg.getRequester();
        String responder = msg.getResponder();
        Worker w1 = workerRepository.findOne(requester);
        Worker w2 = workerRepository.findOne(responder);

        AuthorityMessage result = new AuthorityMessage();
        result.setContext(context);
        result.setAuthorityOk(w1.getAuthorityLvl() >= w2.getAuthorityLvl());
        result.setRequester(requester);
        result.setResponder(responder);

        producerChannel.send(MessageBuilder.withPayload(result).build());
    }
}
