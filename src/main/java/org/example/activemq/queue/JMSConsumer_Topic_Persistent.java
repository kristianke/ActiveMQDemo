package org.example.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer_Topic_Persistent {
    static final String BROKER_URL = "tcp://202.85.220.43:61615";
    static final String TOPIC_NAME = "topic_02";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("I'm a Subscriber 3");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("sc3");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, "remark....");
//        MessageConsumer consumer = session.createConsumer(topic);

        connection.start();

        durableSubscriber.setMessageListener((message)-> {
                if (message != null) {
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            System.out.println("***Msg what Consumer has received: " + textMessage.getText());
                            System.out.println(textMessage.getStringProperty("tag"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    } else if (message instanceof MapMessage) {
                        MapMessage mapMessage = (MapMessage) message;
                        try {
                            System.out.println("*****" + mapMessage.getInt("mkey"));
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        System.in.read();
        durableSubscriber.close();
        session.close();
        connection.close();

    }
}
