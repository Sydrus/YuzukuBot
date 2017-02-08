package net.sydrus.yuzuku.server.recl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class Message {
	private Socket socket;
	private String message;

	public Message(Socket socket, String message) {
		this.socket = socket;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	public SocketAddress getRemoteSocketAddress() {
		return socket.getRemoteSocketAddress();
	}

	public SocketAddress getLocalSocketAddress() {
		return socket.getLocalSocketAddress();
	}

	public OutputStream getOutputStream() throws IOException {
		return socket.getOutputStream();
	}

	public SocketChannel getChannel() {
		return socket.getChannel();
	}

	public String getHostName() {
		return socket.getInetAddress().getHostName();
	}
}
