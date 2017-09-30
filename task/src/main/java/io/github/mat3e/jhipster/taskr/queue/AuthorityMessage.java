package io.github.mat3e.jhipster.taskr.queue;

/**
 * Information about workers authority relation.
 */
public class AuthorityMessage {
    /**
     * Task id in this case.
     */
    private String context;

    /**
     * Task sender.
     */
    private String requester;

    /**
     * Task recipient.
     */
    private String responder;

    /**
     * True when sender could send the task to the recipient.
     */
    private boolean authorityOk;

    public AuthorityMessage() {
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

    public boolean isAuthorityOk() {
        return authorityOk;
    }

    public void setAuthorityOk(boolean authorityOk) {
        this.authorityOk = authorityOk;
    }
}
