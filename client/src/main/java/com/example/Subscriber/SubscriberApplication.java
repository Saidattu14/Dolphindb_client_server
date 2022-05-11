package com.example.Subscriber;

import com.xxdb.data.BasicDate;
import com.xxdb.data.BasicMinute;
import com.xxdb.data.BasicShort;
import com.xxdb.streaming.client.IMessage;
import com.xxdb.streaming.client.PollingClient;
import com.xxdb.streaming.client.TopicPoller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

@SpringBootApplication
public class SubscriberApplication {
	 /**
	 * This is a polling client where it subscribed to the streaming table.
	 * If any new data is added into the table it pull and display the data.
	 */
	public static void main(String[] args) throws SocketException {
		SpringApplication.run(SubscriberApplication.class, args);

		PollingClient client = new PollingClient(8892);
		try {
			TopicPoller poller1 = client.subscribe("localhost", 8900, "heartbeat",0,true);
			while (true) {
				ArrayList<IMessage> msgs = poller1.poll(10000);
				if(msgs != null)
				{
					for (int i = 0; i < msgs.size(); ++i) {

						BasicShort heart1 = (BasicShort) msgs.get(i).getValue(0);
						System.out.println("HeartBeatRate: "+heart1+" ");
						BasicDate date = (BasicDate) msgs.get(i).getValue(1);
						System.out.println("Date:"+date+" ");
						BasicMinute minute = (BasicMinute) msgs.get(i).getValue(2);
						System.out.println("Time:"+minute+"\n");
					}
				}

			}
		} catch (IOException e) {
           System.out.println(e);
		}

	}

}
