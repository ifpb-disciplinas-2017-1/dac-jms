package br.edu.ifpb.infra.jms.mensagem;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue; 

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 28/09/2017, 07:53:32
 */
@Stateless
public class ProdutorDeMensagem {

    @Resource(lookup = "jms/dac/fila")
    private Queue queue;
    @Inject
    @JMSConnectionFactory("jms/dac/ConnectionFactory")
    private JMSContext context;

    public void send(String mensagem) {
        context.createProducer().send(queue, mensagem);
    }
     public String lerMensagem() {
        try {
            JMSConsumer createConsumer = context.createConsumer(queue);
            String mensagem = createConsumer.receiveBody(String.class);
            return String.format("Mensagem:%s lida na fila:%s", mensagem, queue.getQueueName());
        } catch (JMSException ex) {
            return ex.getMessage();
        }
    }
}
