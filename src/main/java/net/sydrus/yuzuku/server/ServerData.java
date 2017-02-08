package net.sydrus.yuzuku.server;

import net.sydrus.yuzuku.server.recl.Connection;
import net.sydrus.yuzuku.server.recl.Message;

public abstract class ServerData {

	public abstract void onReceiveMessage(Message message);

	public abstract void onChooseConnection(Connection connection);

	public abstract void onSendMessage(Message message);

}