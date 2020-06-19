package org.example.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_TX {
    static final String BROKER_URL = "tcp://202.85.220.43:61615";
    static final String QUEUE_NAME = "queue_tx_01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i = 0; i < 10; i++) {
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            producer.send(textMessage);
        }
        producer.close();
        session.commit();
        session.close();
        connection.close();
        System.out.println("Messages had already sent to the mq.");

    }
}
