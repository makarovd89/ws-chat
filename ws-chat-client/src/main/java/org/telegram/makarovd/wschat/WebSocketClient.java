package org.telegram.makarovd.wschat;

interface WebSocketClient {
  void sendMessage(String message);
  void connect();
}
