package net.sydrus.yuzuku.server.recl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class Connection {
	private Socket socket;
	private ConnectionType connection;

	public Connection(Socket socket, ConnectionType connection) {
		this.socket = socket;
		this.connection = connection;
	}

	public ConnectionType getConnection() {
		return this.connection;
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

	public enum ConnectionType {
		Connect, disconnect
	}
}
