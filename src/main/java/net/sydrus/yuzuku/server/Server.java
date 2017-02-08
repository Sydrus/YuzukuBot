package net.sydrus.yuzuku.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import net.sydrus.yuzuku.server.recl.Connection;
import net.sydrus.yuzuku.server.recl.Connection.ConnectionType;

public class Server {
	private ServerSocket serverSocket;
	public boolean isOnline = false;
	private boolean hasClient = false;
	private ServerData listener;
	private HashMap<String, yscue> connecteds = new HashMap<String, yscue>();
	private int port = 0;
	private int tout = 0;
	private Thread check;

	public Server(int port, int timeOut, ServerData listener) throws IOException {
		this.port = port;
		this.tout = timeOut;
		this.listener = listener;
	}

	public void start() throws IOException {
		log("Starting server");
		isOnline = true;
		serverSocket = new ServerSocket(this.port);
		check = new Thread(runCode());
		check.start();
	}

	private Runnable runCode() {
		return new Runnable() {
			public void run() {
				while (isOnline) {
					try {
						serverSocket.setSoTimeout(tout);
						Socket socket = serverSocket.accept();
						yscue user = new yscue(socket, listener, connecteds);
						if (!connecteds.containsKey(socket.getInetAddress().getHostAddress())) {
							user.start();
							connecteds.put(socket.getInetAddress().getHostAddress(), user);
							log("Client Join");
							if (!hasClient)
								hasClient = true;
						} else {
							sendText("This IP is already connected", socket);
							socket.close();
						}
					} catch (SocketTimeoutException e) {
						// System.out.println("[View] " + e.getMessage());
					} catch (Exception e) {
						System.out.println(e);
						System.err.println("\n\n" + e.getMessage());
					}
				}
			}
		};
	}

	public void stop() throws IOException {
		listener.onChooseConnection(new Connection(null, ConnectionType.disconnect));
		log("Closing server");
		for (yscue socket : connecteds.values()) {
			socket.close();
		}
		serverSocket.close();
		isOnline = false;
		hasClient = false;
		serverSocket = null;
		check = null;
	}

	public void sendText(String text, Socket socket) throws IOException {
		// data.onSendMessage(new Message(socket, text));
		byte[] toSendBytes = text.getBytes();
		int toSendLen = toSendBytes.length;
		byte[] toSendLenBytes = new byte[4];
		toSendLenBytes[0] = (byte) (toSendLen & 0xff);
		toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
		toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
		toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);
		socket.getOutputStream().write(toSendLenBytes);
		socket.getOutputStream().write(toSendBytes);
	}

	public int getPort() {
		return this.port;
	}

	private void log(String item) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		System.out.println("[" + hour + ":" + minute + ":" + second + "] " + item);
	}

	public String getStatus() {
		if (hasClient) {
			return "Connected";
		}
		return "Waiting";
	}

	public boolean hasConnection() {
		return hasClient;
	}
}
