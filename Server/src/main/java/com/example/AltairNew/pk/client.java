package com.example.AltairNew.pk;

import com.xxdb.streaming.client.PollingClient;
import com.xxdb.streaming.client.ThreadedClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketException;

public class client {

    @Autowired
    ThreadedClient client;
    public void start() throws SocketException {

//        	PollingClient client = new PollingClient(8892);
//		try {
//			TopicPoller poller1 = client.subscribe("localhost", 8900, "heartbeat");
//			int count = 0;
//			boolean started = false;
//			long start = System.currentTimeMillis();
//			long last = System.currentTimeMillis();
//			while (true) {
//				ArrayList<IMessage> msgs = poller1.poll(10000);
//				if(msgs != null)
//				{
//					for (int i = 0; i < msgs.size(); ++i) {
//
//						BasicShort heart1 = (BasicShort) msgs.get(i).getValue(0);
//						System.out.println("HeartBeatRate: "+heart1+" ");
//						BasicDate date = (BasicDate) msgs.get(i).getValue(1);
//						System.out.println("Date:"+date+" ");
//						BasicMinute minute = (BasicMinute) msgs.get(i).getValue(2);
//						System.out.println("Time:"+minute+"\n");
//					}
//				}
//
//			}
//		} catch (IOException e) {
//            System.out.println(e);
//		}
    }
}
