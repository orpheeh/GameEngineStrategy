package ges.network;

import java.net.Socket;

public interface GESTCPConnect {
    void onConnection(Socket socket);
}
