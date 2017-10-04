package br.edu.ifpb.infra.jms.email;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue; 

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 28/09/2017, 07:53:32
 */
@Stateless
public class Email {

    @Resource(lookup = "jms/dac/fila")
    private Queue queue;
    @Inject
    @JMSConnectionFactory("jms/dac/ConnectionFactory")
    private JMSContext context;

    public void send(String mensagem) {
        context.createProducer().send(queue, mensagem);
    }
}
