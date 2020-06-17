package com.xu.shen.tx;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class TxSend {
	private static final String QUERE_NAME = "test_simple_queue_tx";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtil.getConnection();

		// 3 通过Connection创建一个新的Channel

		Channel channel = connection.createChannel();

		// 发消息
		channel.queueDeclare(QUERE_NAME, false, false, false, null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String msg = "time is " + df.format(new Date());

		try {
			channel.txSelect();

			channel.basicPublish("", QUERE_NAME, null, msg.getBytes());
			int a = 1/0;
			channel.txCommit();
		} catch (Exception e) {
			channel.txRollback();

			System.err.println("channel 回滚");
		} finally {
			channel.close();
			connection.close();
		}

	}

}
