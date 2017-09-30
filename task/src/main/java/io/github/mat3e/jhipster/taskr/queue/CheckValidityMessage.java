package io.github.mat3e.jhipster.taskr.queue;

/**
 * Message for verifying worker data for a given task.
 */
public class CheckValidityMessage {
    /**
     * Id of the task.
     */
    private String context;

    /**
     * Task sender in this case.
     */
    private String requester;

    /**
     * Task recipient in this case.
     */
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
