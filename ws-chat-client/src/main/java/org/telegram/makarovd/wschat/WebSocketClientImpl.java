package org.telegram.makarovd.wschat;

import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler.Whole;
import javax.websocket.Session;

/**
 * https://tyrus-project.github.io/documentation/1.12/index/getting-started.html#d0e79
 */
public class WebSocketClientImpl implements WebSocketClient {
  private final Endpoint endpoint;
  private final URI endpointURI;
  private Session userSession;

  public WebSocketClientImpl(URI endpointURI, Whole<String> messageHandler) {
    this.endpointURI = endpointURI;
    this.endpoint = new Endpoint() {
      @Override
      public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(String.class, messageHandler);
        userSession = session;
      }
    };
  }

  @Override
  public void connect() {
    try {
      ContainerProvider.getWebSocketContainer().connectToServer(endpoint, endpointURI);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void sendMessage(String message) {
    userSession.getAsyncRemote().sendText(message);
  }
}
