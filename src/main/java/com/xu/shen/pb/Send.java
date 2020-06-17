package com.xu.shen.pb;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Send {

	private static final String EXCHANGENAME = "test_exchange_fanout";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();

		Channel channel = connection.createChannel();
		// 声明交换机
		channel.exchangeDeclare(EXCHANGENAME, "fanout");//不处理路由键；Diret 处理路由键
		String msg ="your msg";
		//发送消息
		channel.basicPublish(EXCHANGENAME, "", null, msg.getBytes());
		
		channel.close();
		
		connection.close();
		
	}
}
