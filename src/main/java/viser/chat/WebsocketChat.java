package viser.chat;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/board/Boardlist")
public class WebsocketChat {

	private static Set<Session> clients = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnMessage //클라이언트로 부터 메세지가 도착했을경우
	public void onMessage(String message, Session session) throws IOException {
		System.out.println(message);
		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message
			for (Session client : clients) {
				if (!client.equals(session)) {
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}

	@OnOpen  //
	public void onOpen(Session session) throws IOException {
		onMessage("님이 접속하였습니다.", session);
		System.out.println(session);
		clients.add(session);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		onMessage("님이 퇴장하였습니다.", session);
		// Remove session from the connected sessions set
		clients.remove(session);
	}
	@OnError
    public void onErr(Session session,Throwable t) throws IOException {
		// 자동으로 Onclose호출
    }
}