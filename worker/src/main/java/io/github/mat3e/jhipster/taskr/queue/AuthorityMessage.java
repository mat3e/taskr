package io.github.mat3e.jhipster.taskr.queue;

/**
 * Information about workers authority relation within a given context.
 */
public class AuthorityMessage {
    private String context;
    private String requester;
    private String responder;

    /**
     * True when requester's authority >= responder's authority.
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
