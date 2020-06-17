package com.xu.shen.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Recv2 {
	private static final String QUERE_NAME = "test_queue_confirm2";

	public static void main(String[] args) throws IOException, TimeoutException {
		// TODO Auto-generated method stub
		Connection connection = ConnectionUtil.getConnection();

		// 3 通过Connection创建一个新的Channel

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUERE_NAME, false, false, false, null);

		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				System.err.println("{Recv1}: " + new String(body));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.err.println("{Recv1}:done!");
				}
			}
		};

		channel.basicConsume(QUERE_NAME, true, consumer);

	}

}
