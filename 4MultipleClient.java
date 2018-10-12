package cxf.client;

/**
 * This class tests what if multiple clients try to connect to the same socket
 * 
 * @author xbbncdk
 *
 */
public class MultipleClient {

	public static void main(String[] args) throws InterruptedException {
//		ClientThread client1=new ClientThread();
//		client1.start();
//		ClientThread client2=new ClientThread();
//		client2.start();
//		ClientThread client3=new ClientThread();
//		client3.start();
//		ClientThread client4=new ClientThread();
//		client4.start();
//		
//		ClientThread client5=new ClientThread();
//		client5.start();
//		ClientThread client6=new ClientThread();
//		client6.start();
//		ClientThread client7=new ClientThread();
//		client7.start();
//		ClientThread client8=new ClientThread();
//		client8.start();
		
		//Python client 
//		for(int i=0;i<3;i++)
//		{
//			Thread.currentThread().sleep(2);
//			ClientThread client1=new ClientThread();
//			client1.start();
//		}
		
		// R threads 
		for(int i=0;i<10;i++)
		{
			Thread.currentThread().sleep(2);
			RClientMultiThreading client1=new RClientMultiThreading();
			client1.start();
		}
		System.out.println("invoked...");
		
	}
}
