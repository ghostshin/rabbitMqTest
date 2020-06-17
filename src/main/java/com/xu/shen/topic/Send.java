package com.xu.shen.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Send {
	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String msg = "商品。。。。。。";

//		String routingKey = "goods.add";
		
		String routingKey = "goods.del";

		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
		
		System.out.println("--send---"+msg);

		channel.close();

		connection.close();

	}

}
