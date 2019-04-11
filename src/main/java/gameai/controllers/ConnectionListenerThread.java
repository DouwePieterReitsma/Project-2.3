package gameai.controllers;

import java.io.BufferedOutputStream;
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

import gameai.Main;

public class ConnectionListenerThread implements Runnable {
	private int connectStatus;
	private int port;

	private Socket socket;

	private String host;
	private String username;
	private String loginCommand;
	private String lastResponse;

	private boolean connectionReady;

	private BufferedReader fromServer;
	private BufferedOutputStream toServer;

	private PrintWriter outWriter;

	private int state;
	private int timer;
	private boolean challenge;
	private boolean match;
	private boolean yourTurn;

	private ArrayList<String> commandList;
	private ArrayList<String> playerList;
	private ArrayList<String> gameList;
	private ArrayList<String> challengeList;
	private ArrayList<String> matchList;


	public ConnectionListenerThread(String host, int port, String name) {
		//Set values
		connectStatus = 0;
		this.host = host;
		this.port = port;
		this.username = name;
		
		challenge = false;
		match = false;
		yourTurn = false;


		state = 0; // 0 = login, 1 = mainmenu, 2 = game

		connectionReady = false;

		timer = 100;


		commandList = new ArrayList<String>();
		playerList = new ArrayList<String>();
		gameList = new ArrayList<String>();
		challengeList = new ArrayList<String>();
		matchList = new ArrayList<String>();
	}

	//Getter for connectionstatus
	public int GetConnectStatus() {
		return connectStatus; // 0 = connecting, 1 = failed by host, 2 = failed by duplicate name,  3 = succesfull
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
			toServer = new BufferedOutputStream(new DataOutputStream(socket.getOutputStream()));

			loginCommand = "login " + username + "\n";

			toServer.write(loginCommand.getBytes());
	        toServer.flush(); // send the message

			//Loop
			while(true) {
				ListenToServer();
				if(connectStatus == 3) {
					//Run functions

					//Sleep
					//Thread.sleep(timer);
				}
				else {
					if(connectionReady && connectStatus == 0 || connectionReady && connectStatus == 1 || connectionReady && connectStatus == 2) {
						connectStatus = 3;
					}
					else {
						if(state == 0) {
							ListenToServer();
						}
				        ProcessCommands();
					}
				}
			}
		} catch (UnknownHostException e) {
			connectStatus = 1;
		} catch (IOException e) {
			connectStatus = 1;
		}
	}

	public void UpdatePlayerList() throws IOException, InterruptedException {
		toServer.write("get playerlist\n".getBytes());
		toServer.flush(); // send the message
	}

	public void UpdateGameList() throws IOException, InterruptedException {
		toServer.write("get gamelist\n".getBytes());
		toServer.flush(); // send the message
	}

	public ArrayList<String> GetPlayerList() {
		return playerList;
	}

	public ArrayList<String> GetGameList() {
		return gameList;
	}
	public ArrayList<String> GetChallengeList() {
		return challengeList;
	}
	public boolean getChallenged() {
		return challenge;
	}
	public void setChallFalse() {
		challenge = false;
	}
	public boolean getMatchStatus() {
		return match;
	}
	public void setMatchFalse() {
		match = false;
	}
	public boolean getYourTurn() {
		return yourTurn;
	}
	public void setEnemyTurn() {
		yourTurn = false;
	}

	public void sendCommand(String tekst) throws IOException {
		toServer.write(tekst.getBytes());
		toServer.flush();
	}

	public void ListenToServer() throws IOException {
		//Add to queue
		String data = null;
		while (fromServer.ready()) {
			data = fromServer.readLine();
			commandList.add(data);
		}
		ProcessCommands();
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
				return;
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
				return;
			}
			if(commandList.get(0).contains("SVR GAME MATCH")) {
				String[] firstStep = commandList.get(0).split("\\{");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\}","");
				String fourthStep = thirdStep.replaceAll("\"", "");
				String[] finalResult = fourthStep.split(", ");
				String[] game = finalResult[1].split(": ");
				String[] vijand = finalResult[2].split(": ");
				matchList.clear();
				commandList.remove(0);
				matchList.add(game[0]);
				matchList.add(vijand[0]);
				match= true;
								
				return;
			}
			if(commandList.get(0).contains("SVR GAME YOURTURN")) {
				String[] firstStep = commandList.get(0).split("\\{");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\}","");
				String finalResult = thirdStep.replaceAll("\"", ""); 
				String[] message = finalResult.split(": ");
				
				commandList.remove(0);
				yourTurn= true;
				return;
			}
			if(commandList.get(0).contains("SVR GAME MOVE")) {
				String[] firstStep = commandList.get(0).split("\\{");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\}","");
				String fourthStep = thirdStep.replaceAll("\"", "");
				String[] finalResult = fourthStep.split(", ");
				String[] speler = finalResult[0].split(": ");
				String[] details = finalResult[1].split(": ");
				String[] move = finalResult[2].split(": ");
				
				commandList.remove(0);
				
				return;
			}else if(commandList.get(0).contains("SVR GAME CHALLENGE")) {
				String[] firstStep = commandList.get(0).split("\\{");
				String secondStep = firstStep[1];
				String thirdStep = secondStep.replaceAll("\\}","");
				String fourthStep = thirdStep.replaceAll("\"", "");
				String[] finalResult = fourthStep.split(", ");
				String[] speler = finalResult[0].split(": ");
				String[] nummer = finalResult[1].split(": ");
				String[] game = finalResult[2].split(": ");
				challengeList.clear();
				
				commandList.remove(0);
				challengeList.add(speler[1]);
				challengeList.add(nummer[1]);
				challengeList.add(game[1]);
				
				challenge = true;

				return;
			}
		}

		if(commandList.size() > 0) {
			//Then use switch case
			switch(commandList.get(0)) {
				case "OK":
					if(state == 0) {
						state++;
						System.out.println("State highered!");
						connectionReady = true;
						timer = 1000;
					}
					commandList.remove(0);
					return;
				case "ERR Duplicate name exists":
					connectStatus = 2;
					return;

				default:
					commandList.remove(0);
					return;
			}
		}
	}
}
