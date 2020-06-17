package com.xu.shen.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Recv2 {
	private static final String QUEUE_NAME = "test_work_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取链接
		Connection connection = ConnectionUtil.getConnection();
		// 创建信道
		Channel channel = connection.createChannel();
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

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
				}
				finally{
					System.err.println("{Recv2}:done!");
				}
			}
		};
		
		channel.basicConsume(QUEUE_NAME,true, consumer);

	}
}
