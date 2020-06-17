package com.xu.shen.workqueuefair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Recv1 {
	private static final String QUEUE_NAME = "test_work_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取链接
		Connection connection = ConnectionUtil.getConnection();
		// 创建信道
		final Channel channel = connection.createChannel();
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/*
		 * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者。
		 * 一次只处理一个消息
		 * 
		 * 限制发送给同一个消费者不得超过1条消息
		 * 
		 * */
		channel.basicQos(1);
		


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
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					System.err.println("{Recv1}:done!");
					//发送回执
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		
		
		boolean autuAck = false;
		channel.basicConsume(QUEUE_NAME,autuAck, consumer);

	}
}
