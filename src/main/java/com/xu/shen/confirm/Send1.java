package com.xu.shen.confirm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Send1 {
	private static final String QUEUE_NAME = "test_queue_confirm";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();

		// 3 通过Connection创建一个新的Channel

		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String msg = "time is " + df.format(new Date());

		// 生产者调用confirmSelect 将channel设置成confirm模式 注意，已声明为txSelect的队列，不可改成该模式！
		channel.confirmSelect();
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

		if (!channel.waitForConfirms()) {
			System.err.println("message send failed");
		} else {
			System.err.println("message send success");
		}

	}

}
