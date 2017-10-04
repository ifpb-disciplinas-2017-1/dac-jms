package br.edu.ifpb.web;

import br.edu.ifpb.infra.jms.email.ProdutorDeEmail;
import br.edu.ifpb.infra.jms.mensagem.ProdutorDeMensagem;
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

    private String mensagem;
    private String email;

    @Inject
    private ProdutorDeEmail service;

    public String enviar() {
        this.service.emailComDestinatario(mensagem, email);
        this.mensagem = "";
        this.email = "";
        return null;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
