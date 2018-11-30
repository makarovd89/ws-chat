package org.telegram.makarovd.wschat;

import static org.glassfish.tyrus.client.ClientProperties.AUTH_CONFIG;
import static org.glassfish.tyrus.client.ClientProperties.CREDENTIALS;

import java.net.URI;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.auth.AuthConfig;
import org.glassfish.tyrus.client.auth.Credentials;

/*
 * https://tyrus-project.github.io/documentation/1.13.1/index/getting-started.html#d0e79
 * https://tyrus-project.github.io/documentation/1.13.1/user-guide.html#d0e1658
 */
public class WebSocketClientImpl implements WebSocketClient {
  private final URI uri;
  private final ClientManager clientManager;
  private final Endpoint endpoint;
  private Session userSession;

  public WebSocketClientImpl(URI endpointURI, Whole<String> messageHandler) {
    uri = endpointURI;
    clientManager = ClientManager.createClient();
    clientManager.getProperties().put(AUTH_CONFIG, AuthConfig.Builder.create().build());
    endpoint = new Endpoint() {
      @Override
      public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(String.class, messageHandler);
        userSession = session;
      }
    };
  }

  @Override
  public void connect(String user, String password) throws Exception {
    clientManager.getProperties().put(CREDENTIALS, new Credentials(user, password));
    clientManager.connectToServer(endpoint, uri);
  }

  @Override
  public void sendMessage(String message) {
    userSession.getAsyncRemote().sendText(message);
  }
}
