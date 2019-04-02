package gameai.controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionListenerThread implements Runnable {
	private int connectStatus;
	private int port;

	private Socket socket;

	private String host;
	private String username;
	private String loginCommand;

	private DataInputStream fromServer;
	private DataOutputStream toServer;

	private PrintWriter outWriter;

	public ConnectionListenerThread(String host, int port, String name) {
		//Set values
		connectStatus = 0;
		this.host = host;
		this.port = port;
		this.username = name;
	}

	//Getter for connectionstatus
	public int GetConnectStatus() {
		return connectStatus; // 0 = connecting, 1 = failed, 2 = succesfull
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

			Thread.sleep(1000);

			toServer.writeUTF(loginCommand);
	        toServer.flush(); // send the message

			connectStatus = 2;
		} catch (UnknownHostException e) {
			connectStatus = 1;
		} catch (IOException e) {
			connectStatus = 1;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
