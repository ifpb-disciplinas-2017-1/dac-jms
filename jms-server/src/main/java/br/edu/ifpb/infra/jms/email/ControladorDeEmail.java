package br.edu.ifpb.infra.jms.email;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Ricardo Job
 */
@Named
@RequestScoped
public class ControladorDeEmail {

    @Inject
    private Email email;

    private String mensagem;

    public String enviar() {
        email.send(mensagem);
        return null;
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
