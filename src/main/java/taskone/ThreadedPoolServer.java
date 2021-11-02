/**
  File: ThreadedPoolServer.java
  Author: Noah O'Brien
  Description: ThreadedPoolServer class in package taskone.
*/

package taskone;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/**
 * Class: Server
 * Description: Server tasks.
 */
class ThreadedPoolServer {

	public static int connections;
	public static AtomicInteger counter = new AtomicInteger(0);

	public static void main(String[] args) throws Exception {

		if (args.length != 1) {

            		// gradle runTask3 -Pconnections=n
            		System.out.println("Usage: gradle runTask3 -Pconnections=n, with n being number of connections.");
            		System.exit(1);

       		}

        	try {

            		connections = Integer.parseInt(args[0]);

        	}catch(NumberFormatException nfe) {

            		System.out.println("[Connection] must be an integer.");
            		System.exit(2);

        	}

		ServerSocket server = null;

		try{

			server = new ServerSocket(8000);
			System.out.println("Server Started...");
			server.setReuseAddress(true);
			
			while(true){

				StringList strings = new StringList();

				if(counter.get() == connections) {

					
					while(counter.get() == connections) { }
					Socket client = server.accept();

				}

				Socket client = server.accept();
				ThreadedPoolServer.counter.getAndIncrement();

				System.out.println("Accepting request number " + counter + ".");

				Performer performer = new Performer(client, strings);

				new Thread() {

    					public void run() {

        					performer.doPerform();
						try {
                					System.out.println("close socket of client ");
							counter.getAndDecrement();
                					client.close();
            					} catch (Exception e) {
                					e.printStackTrace();
            					}

    					}

				}.start();

			}

		}catch(IOException e){

			e.printStackTrace();

		}

		finally{

			if(server != null){

				try{

					server.close();

				}catch(IOException e){

					e.printStackTrace();

				}

			}

		}
	
	}


}	









