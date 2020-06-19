package org.example.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persistent {
    static final String BROKER_URL = "tcp://202.85.220.43:61615";
    static final String TOPIC_NAME = "topic_02";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();


        for (int i = 2; i < 4; i++) {
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            textMessage.setStringProperty("tag","vip");
            producer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setInt("mkey",i*i);
            producer.send(mapMessage);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("Topic Messages had already sent to the mq.");

    }
}
