package com.xu.shen.pb;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Recv2 {
	private static final String EXCHANGENAME = "test_exchange_fanout";
	private static final String QUEUENAME = "test_queue_fanout_sms";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();

		final Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUENAME, false, false, false, null);
		
		//绑定到交换机
		
		channel.queueBind(QUEUENAME, EXCHANGENAME, "");
		
		channel.basicQos(1);

		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				// System.err.println("-----------consume message----------");
				// System.err.println("consumerTag: " + consumerTag);
				// System.err.println("envelope: " + envelope);
				// System.err.println("properties: " + properties);
				System.err.println("{Recv2}: " + new String(body));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.err.println("{Recv2}:done!");
					// 发送回执
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		boolean autuAck = false;
		channel.basicConsume(QUEUENAME, autuAck, consumer);
		
	}
}
