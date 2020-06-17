package com.xu.shen.confirm;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.xu.shen.rabbitmqutil.ConnectionUtil;

public class Send3 {
	private static final String QUEUE_NAME = "test_queue_confirm3";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();

		// 3 通过Connection创建一个新的Channel

		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 生产者调用confirmSelect 将channel设置成confirm模式 注意，已声明为txSelect的队列，不可改成该模式！
		channel.confirmSelect();

		final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

		channel.addConfirmListener(new ConfirmListener() {
			// 没问题的
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				// TODO Auto-generated method stub
				if (multiple) {
					confirmSet.headSet(deliveryTag + 1).clear();
				} else {
					confirmSet.remove(deliveryTag);
				}

			}

			// 有问题的
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				// TODO Auto-generated method stub
				if (multiple) {
					confirmSet.headSet(deliveryTag + 1).clear();
				} else {
					confirmSet.remove(deliveryTag);
				}
			}

		});

	}

}
