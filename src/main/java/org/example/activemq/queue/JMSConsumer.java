package org.example.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    static final String BROKER_URL = "tcp://202.85.220.43:61615";
    static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        while (true){
            TextMessage textMessage = (TextMessage) consumer.receive(3000L);
            if (textMessage != null){
                System.out.println("****Received Message******:" + textMessage.getText());
            }else {
                break;
            }
        }

        consumer.close();
        session.close();
        connection.close();

    }
}
