package br.edu.ifpb.infra.jms.email;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 25/09/2017, 08:42:04
 */
@JMSDestinationDefinition(
        name = "java:global/jms/topic/aula",
        resourceAdapter = "jmsra",
        interfaceName = "javax.jms.Topic",
        destinationName = "topic",
        description = "Topic em aula")
@Stateless
public class ProdutorDeEmail {

    @Resource(lookup = "java:global/jms/topic/aula")
    private Topic fila;

    @Inject
//    @JMSConnectionFactory("jms/__defaultConnectionFactory")
    private JMSContext context;

    public void email(String email) {
        context.createProducer().send(fila, email);
    }

    public void emailComDestinatario(String mensagem, String emailFrom) {
        try {
            JMSProducer createProducer = context.createProducer();
            String msg = String.format("from:%s msg:%s", emailFrom, mensagem);
            TextMessage createTextMessage = context.createTextMessage(msg);
            createTextMessage.setStringProperty("email", emailFrom);
            createProducer.send(fila, createTextMessage);
        } catch (JMSException ex) {
            Logger.getLogger(ProdutorDeEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
