package br.edu.ifpb.infra.jms.email;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 28/09/2017, 08:15:06
 */
@MessageDriven(
mappedName = "java:global/jms/topic/aula",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic"),
                @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "email='job'")
        }
)
public class ConsumidorDeEmail implements MessageListener{

    @Override
    public void onMessage(Message message) {
        System.out.println("Processando o email...");
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException ex) {
            Logger.getLogger(ConsumidorDeEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
