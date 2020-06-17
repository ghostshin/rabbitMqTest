package com.xu.shen.rabbitmqutil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

	/**
	 * 工具类
	 * 
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public static Connection getConnection() throws IOException, TimeoutException {
		// 1 创建ConnectionFactory

		ConnectionFactory factory = new ConnectionFactory();

		// IP

		factory.setHost("192.168.31.84");
		// 端口

		factory.setPort(5672);
		// vhost
		factory.setVirtualHost("/vhost_dep");

		factory.setUsername("dep_user");

		factory.setPassword("1024");

		return factory.newConnection();
	}
}