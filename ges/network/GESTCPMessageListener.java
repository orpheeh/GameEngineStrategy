package ges.network;

import java.net.Socket;

public interface GESTCPMessageListener {

    void onMessageReceive(Socket socket, String message);
}
