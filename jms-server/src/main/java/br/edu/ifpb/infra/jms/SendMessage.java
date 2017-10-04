package br.edu.ifpb.infra.jms;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
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
//@MessageDriven(
//        name = "testmdb",activationConfig = {
//        @ActivationConfigProperty(propertyName = "destinationType",propertyValue = "javax.jms.Topic"),
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "Topic"),
//        @ActivationConfigProperty(propertyName = "destinationLookup",propertyValue = "java:global/jms/demoTopic"),
//        @ActivationConfigProperty(propertyName = "messageSelector",propertyValue = "sexo = 'homem'")})
//@JMSDestinationDefinition(
//        name = "jms/dac/aula",
//        interfaceName = "javax.jms.Queue",
//        destinationName = "aula"
//)
//@JMSDestinationDefinition(
//        name = "jms/demoTopic",
//        description = "Topic  usada na aula",
//        interfaceName = "javax.jms.Topic"
//)
//@JMSDestinationDefinitions({
@JMSDestinationDefinition(
        name = "java:global/jms/topic/aula",
        resourceAdapter = "jmsra",
        interfaceName = "javax.jms.Topic",
        destinationName = "topic",
        description = "Topic em aula")
//    })

@Stateless
public class SendMessage {

    @Resource(lookup = "java:global/jms/topic/aula")
    private Topic fila;
//    @Resource(lookup = "jms/dac/fila")
//    private Queue fila;

    @Inject
//    @JMSConnectionFactory("jms/__defaultConnectionFactory")
    private JMSContext context;

    public void enviarNovaMensagem(String mensagem) {
        context.createProducer().send(fila, mensagem);
    }

    public String lerMensagem() {
        try {
            JMSConsumer createConsumer = context.createConsumer(fila);
//            Message receive = createConsumer.receive();
            String mensagem = createConsumer.receiveBody(String.class);
//            return String.format("Mensagem:%s lida na fila:%s", mensagem, fila.getQueueName());
            return String.format("Mensagem:%s lida no Topic:%s", mensagem, fila.getTopicName());
            
            
            
        } catch (JMSException ex) {
            return ex.getMessage();
        }
    }

    public void enviarNovaMensagem(String mensagem, String email) {
        try {
            JMSProducer createProducer = context.createProducer();
            String msg = String.format("from:%s msg:%s", email, mensagem);
            TextMessage createTextMessage = context.createTextMessage(msg);
            createTextMessage.setStringProperty("email", email);
            createProducer.send(fila, createTextMessage);
        } catch (JMSException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
