package com.xu.shen.workqueuefair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Send {

	private static final String QUEUE_NAME = "test_work_queue";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		// TODO Auto-generated method stub
		// 获取链接

		Connection connection = ConnectionUtil.getConnection();
		//创建信道
		Channel channel = connection.createChannel();
		//声明队列
		
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/*
		 * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者。
		 * 一次只处理一个消息
		 * 
		 * 限制发送给同一个消费者不得超过1条消息
		 * 
		 * */
		channel.basicQos(1);
		
		//发送消息
		for (int i = 0; i < 51; i++) {
			String msg = "send msg times : " + i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(1000);
		}
		channel.close();
		connection.close();

	}

}
