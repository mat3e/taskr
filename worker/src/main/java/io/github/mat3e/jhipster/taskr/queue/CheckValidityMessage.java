package io.github.mat3e.jhipster.taskr.queue;

/**
 * Message for verifying worker data in a given context.
 */
public class CheckValidityMessage {
    private String context;
    private String requester;
    private String responder;

    public CheckValidityMessage() {
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getResponder() {
        return responder;
    }

    public void setResponder(String responder) {
        this.responder = responder;
    }
}
