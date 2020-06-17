package com.xu.shen.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Recv1 {
	private static final String EXCHANGE_NAME = "test_exchange_topic";

	private static final String QUEUE_NAME = "test_queue_topic1";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();

		final Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		channel.basicQos(1);

		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");

		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				// System.err.println("-----------consume message----------");
				// System.err.println("consumerTag: " + consumerTag);
				// System.err.println("envelope: " + envelope);
				// System.err.println("properties: " + properties);
				System.err.println("{Recv1}: " + new String(body));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.err.println("{Recv1}:done!");
					// 发送回执
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		boolean autuAck = false;
		channel.basicConsume(QUEUE_NAME, autuAck, consumer);

	}

}
