/**
  File: Server.java
  Author: Noah O'Brien
  Description: ThreadedServer class in package taskone.
*/

package taskone;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import org.json.JSONObject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class: Server
 * Description: Server tasks.
 */
class ThreadedServer {

	public static AtomicInteger counter = new AtomicInteger(0);

	public static void main(String[] args) throws Exception {

		ServerSocket server = null;

		try{

			server = new ServerSocket(8000);
			System.out.println("Server Started...");
			server.setReuseAddress(true);
			
			while(true){

				StringList strings = new StringList();
				Socket client = server.accept();
				counter.getAndIncrement();
				System.out.println("Accepting request number " + counter.get() + ".");

				Performer performer = new Performer(client, strings);

				new Thread() {

    					public void run() {

						int clientNum = counter.get();
        					performer.doPerform();

						try {

                					System.out.println("Client " + clientNum + " left the game.");
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














