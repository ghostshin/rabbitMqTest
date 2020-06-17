package com.xu.shen.simple;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class SendMessage {

	private static final String QUERE_NAME = "test_simple_queue";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		while (true) {
			sendMessage();

			Thread.sleep(2000);
		}

	}

	private static void sendMessage() throws IOException, TimeoutException {
		// 2 获取Connection

		Connection connection = ConnectionUtil.getConnection();

		// 3 通过Connection创建一个新的Channel

		Channel channel = connection.createChannel();

		// 发消息
		channel.queueDeclare(QUERE_NAME, false, false, false, null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String msg = "time is " + df.format(new Date());

		channel.basicPublish("", QUERE_NAME, null, msg.getBytes());

		System.out.println("---------sendmsg");

		channel.close();

		connection.close();
	}

}
