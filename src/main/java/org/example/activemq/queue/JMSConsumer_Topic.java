package org.example.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer_Topic {
    static final String BROKER_URL = "tcp://202.85.220.43:61615";
    static final String TOPIC_NAME = "topic_01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("I'm a Consumer 1");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(message -> {
                    if (message != null && message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            System.out.println("****Msg what Consumer has received: " + textMessage.getText());
                            System.out.println(textMessage.getStringProperty("tag"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }else if (message != null && message instanceof MapMessage){
                        MapMessage mapMessage = (MapMessage) message;
                        try {
                            System.out.println("*****" + mapMessage.getInt("mkey"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }
}
