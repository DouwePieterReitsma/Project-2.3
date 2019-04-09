package gameai.controllers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

	private BufferedReader fromServer;
	private DataOutputStream toServer;
	
	private PrintWriter outWriter;

	private int state;
	private int timer;

	private ArrayList<String> commandList;
	private ArrayList<String> playerList;
	private ArrayList<String> gameList;
	

	public ConnectionListenerThread(String host, int port, String name) {
		//Set values
		connectStatus = 0;
		this.host = host;
		this.port = port;
		this.username = name;
		

		state = 0; // 0 = login, 1 = mainmenu, 2 = game

		timer = 100;
		

		commandList = new ArrayList<String>();
		playerList = new ArrayList<String>();
		gameList = new ArrayList<String>();
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
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//Create outputstream to send
			toServer = new DataOutputStream(socket.getOutputStream());

			loginCommand = "login " + username + "\n";

			Thread.sleep(100);

			toServer.writeUTF(loginCommand);
	        toServer.flush(); // send the message

			connectStatus = 2;

			//Loop
			while(true) {
				//Check Commands
				if(state == 0) {
					ListenToServer();
				}

				//Run functions
				ProcessCommands();

				//Sleep
				Thread.sleep(timer);
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

	public void UpdatePlayerList() throws IOException {
			toServer.writeUTF("get playerlist\n");
			toServer.flush(); // send the message
			ListenToServer();
		
	}

	public void UpdateGameList() throws IOException {
		toServer.writeUTF("get gamelist\n");
		toServer.flush(); // send the message
		ListenToServer();
		
	}

	public ArrayList<String> GetPlayerList() {
		return playerList;
	}

	public ArrayList<String> GetGameList() {
		return gameList;
	}
	public void subben(String tekst) throws IOException {
		toServer.writeUTF(tekst);
		toServer.flush();
		ListenToServer();
	}

	private void ListenToServer() throws IOException {
		//Add to queue
		commandList.add(fromServer.readLine());
	}

	private void ProcessCommands() {
		if(commandList.size() > 0) {
			System.out.println(commandList.get(0));

			//Check if its a server command first
			if(commandList.get(0).contains("SVR PLAYERLIST")) {
				//System.out.println("works!");
				String[] firstStep = commandList.get(0).split("\\[");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\]","");
				String fourthStep = thirdStep.replaceAll("\"", "");
				String[] finalResult = fourthStep.split(", ");
				playerList.clear();
				for(int i = 0; i < finalResult.length; i++) {
					playerList.add(finalResult[i]);
				}
				commandList.remove(0);
			}
			if(commandList.get(0).contains("SVR GAMELIST")) {
				String[] firstStep = commandList.get(0).split("\\[");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\]","");
				String fourthStep = thirdStep.replaceAll("\"", "");
				String[] finalResult = fourthStep.split(", ");
				gameList.clear();
				for(int i = 0; i < finalResult.length; i++) {
					gameList.add(finalResult[i]);
				}
				commandList.remove(0);
			}else if(commandList.get(0).contains("SVR GAME CHALLENGE")) {
				String[] firstStep = commandList.get(0).split("\\{");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\}","");
				String fourthStep = thirdStep.replaceAll("\"", "");
				String[] finalResult = fourthStep.split(", ");
				for(int i = 0; i < finalResult.length; i++) {
					System.out.println(finalResult[i]);
				}
				
				commandList.remove(0);
			}

			//Then use switch case
			switch(commandList.get(0)) {
				case "OK":
					if(state == 0) {
						state++;
						System.out.println("State highered!");
						timer = 1000;
					}
					commandList.remove(0);
					break;

				default:
					commandList.remove(0);
					break;
			}
		}
	}
}
