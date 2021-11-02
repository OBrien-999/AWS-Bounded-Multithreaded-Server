/**
  File: Performer.java
  Author: Noah O'Brien
  Description: Performer class in package taskone.
*/

package taskone;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Class: Performer 
 * Description: Threaded Performer for server tasks.
 */
class Performer {

    private StringList state;
    private Socket conn;

    public Performer(Socket sock, StringList strings) {
        this.conn = sock;
        this.state = strings;
    }

    public JSONObject add(String str) {
        JSONObject json = new JSONObject();
        json.put("datatype", 1);
        json.put("type", "add");
        state.add(str);
        json.put("data", state.toString());
        return json;
    }

    public JSONObject pop(){

	String display;
	JSONObject json = new JSONObject();
	
	json.put("datatype", 2);
	json.put("type", "remove");
	display = state.remove();

	if(display.equals("null")){
		
	        return error("null");

	}else{

		json.put("data", display);
		return json;

	}
	
    }

     public JSONObject display(){

	JSONObject json = new JSONObject();
	json.put("datatype", 3);
	json.put("type", "display");
	json.put("data", state.toString());
	return json;   
	
    }

     public JSONObject count(){

	JSONObject json = new JSONObject();
	json.put("datatype", 4);
	json.put("type", "count");
	String count = String.valueOf(state.size());
	json.put("data", count);
	return json;
	
    }

    public JSONObject switching(int x, int y){

	JSONObject json = new JSONObject();
	json.put("datatype", 5);
	json.put("type", "reverse");

	if(x <= state.size() - 1 && y <= state.size() - 1 && x >= 0 && y >= 0){
	    
	state.flip(x, y);
	json.put("data", state.toString());
	return json;

	}else{

	    return error("null"); 

	}
	
    }

    public static JSONObject quit(){

	String exit = "Exiting...";
	JSONObject json = new JSONObject();
	json.put("datatype", 0);
	json.put("type", "quit");
	json.put("data", exit);
	return json;

    }

    public static JSONObject error(String err) {
        JSONObject json = new JSONObject();
        json.put("error", err);
        return json;
    }

    public void doPerform() {
        boolean quit = false;
        OutputStream out = null;
        InputStream in = null;
        try {

            out = conn.getOutputStream();
            in = conn.getInputStream();

            System.out.println("Server connected to client:");
            while (!quit) {
                byte[] messageBytes = NetworkUtils.receive(in);
                JSONObject message = JsonUtils.fromByteArray(messageBytes);
                JSONObject returnMessage = new JSONObject();
   
                int choice = message.getInt("selected");
                    switch (choice) {
		        case(0):
			    returnMessage = quit();
			    quit = true;
			    break;
                        case (1):
                            String inStr = (String) message.get("data");
                            returnMessage = add(inStr);
                            break;
		        case (2):
			    returnMessage = pop();
			    break;
		        case (3):
			    returnMessage = display();
			    break;
		        case (4):
			    returnMessage = count();
			    break;
		        case (5):
			    String inString = (String) message.get("data");
			    String first = inString.substring(0, inString.indexOf(" "));
			    String last = inString.substring(inString.indexOf(" ") + 1);
			    int x = Integer.parseInt(first);
			    int y = Integer.parseInt(last);
			    returnMessage = switching(x, y);
			    break;
                        default:
                            returnMessage = error("Invalid selection: " + choice 
                                    + " is not an option");
                            break;
                    }
                // we are converting the JSON object we have to a byte[]
                byte[] output = JsonUtils.toByteArray(returnMessage);
                NetworkUtils.send(out, output);
            }
            // close the resource
            System.out.println("close the resources of client ");
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("The max amount of clients are already connected... trying again.");
        }
    }
}
