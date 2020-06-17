package com.xu.shen.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;

import com.rabbitmq.client.ShutdownSignalException;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

/**
 * 
 * 消费者接收消息
 * 
 * @author xlelo
 *
 */
public class Recv {
	private static final String QUERE_NAME = "test_simple_queue";

	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {
		// 获取链接
		Connection connection = ConnectionUtil.getConnection();

		// 获取信道

		Channel channel = connection.createChannel();
		
		//自定义消费者
		MyConsumer myConsumer = new MyConsumer(channel);
		
		
		//监听队列
		channel.basicConsume(QUERE_NAME, true, myConsumer);

	}

}
