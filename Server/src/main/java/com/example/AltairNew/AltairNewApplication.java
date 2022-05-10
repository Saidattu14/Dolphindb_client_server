package com.example.AltairNew;

import ch.qos.logback.core.net.server.Client;
import com.example.AltairNew.pk.client;
import com.xxdb.data.*;
import com.xxdb.streaming.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class AltairNewApplication {


	public static void main(String[] args) throws SocketException {

		SpringApplication.run(AltairNewApplication.class, args);
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

		}

	}
}
