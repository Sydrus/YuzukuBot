package net.sydrus.yuzuku.Listeners;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.server.ServerData;
import net.sydrus.yuzuku.server.recl.Connection;
import net.sydrus.yuzuku.server.recl.Message;
import net.sydrus.yuzuku.server.recl.Connection.ConnectionType;

public class ServerListener extends ServerData {

	@Override
	public void onReceiveMessage(Message message) {
		System.out.println("[" + message.getHostName() + "] " + message.getMessage());

	}

	@Override
	public void onChooseConnection(Connection connection) {
		if (connection.getConnection() == ConnectionType.Connect) {
			YuzukuBot.getInstance().connectionOnlineTime.clear();
			YuzukuBot.getInstance().connectionOnlineTime.startCount();
			System.out.println(connection.getInetAddress().getHostAddress());
		} else if (connection.getConnection() == ConnectionType.disconnect) {
			YuzukuBot.getInstance().connectionOnlineTime.stopCount();
		}
	}

	@Override
	public void onSendMessage(Message message) {
		System.out.println("[Console] " + message.getMessage());
	}

}
