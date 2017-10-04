package br.edu.ifpb;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/09/2017, 22:41:09
 */
public class Main {

    public static void main(String[] args) throws JMSException {
        Main m = new Main();
//        Queue queue = m.lookup("jms/dac/fila");
        Topic topic = m.lookup("java:global/jms/topic/aula");
        ConnectionFactory con = m.lookup("jms/dac/ConnectionFactory");
        JMSContext context = con.createContext();
        TextMessage textMessage = context.createTextMessage("Enviado pelo JSE");
        textMessage.setStringProperty("email", "job");
        context.createProducer().send(topic, textMessage);

    }

    public <T> T lookup(String resource) {
        try {
            Properties properties = new Properties();
            properties.put(InitialContext.INITIAL_CONTEXT_FACTORY,
                    "com.sun.enterprise.naming.SerialInitContextFactory");
            properties.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
//            properties.setProperty(InitialContext.PROVIDER_URL, "mq://localhost:7676/");
            InitialContext context = new InitialContext(properties);
            return (T) context.lookup(resource);
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
