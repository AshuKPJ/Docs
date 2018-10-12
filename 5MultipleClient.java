package com.bny.analytics.mlengine.client;

/**
 * This class can be used to spawn multiple RClient / Python clients to the MlEngine 
 * Service 
 * This class can be used for testing. 
 * 
 * 
 * 
 * @author xbbncdk
 *
 */
public class MultipleClient {

	public static void main(String[] args) throws InterruptedException {
		
		//Python client 
//		for(int i=0;i<20;i++)
//		{
//			Thread.currentThread().sleep(2);
//			ClientThread client1=new ClientThread();
//			client1.start();
//		}
		
		// R threads 
		for(int i=0;i<100;i++)
		{
			Thread.currentThread().sleep(2);
			RClientMultiThreading client=new RClientMultiThreading();
			client.start();
		}
		System.out.println("100 R Clients invoked...");
		
	}
}
