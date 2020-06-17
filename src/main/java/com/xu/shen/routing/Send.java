package com.xu.shen.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Send {

	private static final String EXCHANGE_NAME = "test_exchange_direct";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();

		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct");

		String msg = "Hello direct!";

		String routingKey = "info";

		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

		channel.close();

		connection.close();
	}

}
