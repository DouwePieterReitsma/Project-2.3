package gameai.controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionListenerThread implements Runnable {
	private int connectStatus;
	private int port;

	private Socket socket;

	private String host;
	private String username;
	private String loginCommand;
	private String lastResponse;

	private DataInputStream fromServer;
	private DataOutputStream toServer;

	private PrintWriter outWriter;

	private int state;

	private Queue<String> commandQueue;

	public ConnectionListenerThread(String host, int port, String name) {
		//Set values
		connectStatus = 0;
		this.host = host;
		this.port = port;
		this.username = name;

		state = 0; // 0 = login, 1 = mainmenu, 2 = game

		commandQueue = new LinkedList<String>();
	}

	//Getter for connectionstatus
	public int GetConnectStatus() {
		return connectStatus; // 0 = connecting, 1 = failed, 2 = succesfull
	}

	//Getter for state
	public int GetState() {
		return state;
	}

	public void run() {
		connectStatus = 0;

		//Connect
		try {
			socket = new Socket(host, port);

			//Create inputstream to receive
			fromServer = new DataInputStream(socket.getInputStream());
			//Create outputstream to send
			toServer = new DataOutputStream(socket.getOutputStream());

			loginCommand = "login " + username + "\n";

			Thread.sleep(100);

			toServer.writeUTF(loginCommand);
	        toServer.flush(); // send the message

			connectStatus = 2;

			//Loop
			while(true) {
				ListenToServer();

				//Check state
				if(state == 0 && ProcessCommands().equals("success")) {
					state++;
					System.out.println("true");
				}

				//Thread.sleep(1000);
			}
		} catch (UnknownHostException e) {
			connectStatus = 1;
		} catch (IOException e) {
			connectStatus = 1;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ListenToServer() throws IOException {
		//Add to queue
		System.out.println(fromServer.read().ToString());
		commandQueue.add(fromServer.readUTF());
	}

	private String ProcessCommands() {
		switch(commandQueue.element()) {
			case "OK":
				commandQueue.remove();
				return "success";
			case "YOURTURN":
				commandQueue.remove();
				return "true";
			//case ""
				//break;

			default:
				commandQueue.remove();
				return "";
		}
	}
}
