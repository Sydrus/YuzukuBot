package net.sydrus.yuzuku.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import net.sydrus.yuzuku.server.recl.Connection;
import net.sydrus.yuzuku.server.recl.Message;
import net.sydrus.yuzuku.server.recl.Connection.ConnectionType;

public class yscue implements Runnable {

	private Thread thread;
	private InputStream is;
	private OutputStream os;
	private ServerData listener;
	private Socket socket;
	private HashMap<String, yscue> uconnecteds;

	public yscue(Socket socket, ServerData listener, HashMap<String, yscue> connecteds) {
		this.socket = socket;
		this.listener = listener;
		this.uconnecteds = connecteds;
	}

	public void run() {
		while (true) {
			try {
				byte[] lenBytes = new byte[4];
				is.read(lenBytes, 0, 4);
				int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) | ((lenBytes[1] & 0xff) << 8)
						| (lenBytes[0] & 0xff));
				byte[] receivedBytes = new byte[len];
				is.read(receivedBytes, 0, len);
				String received = new String(receivedBytes, 0, len);
				if (len == 0) {
					close();
					break;
				}
				listener.onReceiveMessage(new Message(socket, received));
			} catch (IOException e) {
				log("Client Leave");

				try {
					close();
				} catch (IOException e1) {
					e1.getMessage();
				}

				break;
			}
		}
	}

	public void sendText(String text) throws IOException {
		listener.onSendMessage(new Message(socket, text));
		byte[] toSendBytes = text.getBytes();
		int toSendLen = toSendBytes.length;
		byte[] toSendLenBytes = new byte[4];
		toSendLenBytes[0] = (byte) (toSendLen & 0xff);
		toSendLenBytes[1] = (byte) ((toSendLen >> 8) & 0xff);
		toSendLenBytes[2] = (byte) ((toSendLen >> 16) & 0xff);
		toSendLenBytes[3] = (byte) ((toSendLen >> 24) & 0xff);
		os.write(toSendLenBytes);
		os.write(toSendBytes);
	}

	public void start() throws IOException {
		try {
			os = socket.getOutputStream();
			is = socket.getInputStream();
			thread = new Thread(this);
			thread.start();
			listener.onChooseConnection(new Connection(socket, ConnectionType.Connect));
		} catch (IOException e) {
			close();
			throw e;
		}

	}

	public void close() throws IOException {
		try {
			if (uconnecteds.containsKey(socket.getInetAddress().getHostAddress())) {
				uconnecteds.remove(socket.getInetAddress().getHostAddress());
			}
		} catch (Exception e) {
		}
		thread = null;
		os.close();
		is.close();
		socket.close();
		os = null;
		is = null;
	}

	private void log(String item) {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		System.out.println("[" + hour + ":" + minute + ":" + second + "] " + item);
	}

}
