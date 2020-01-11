package ges.network;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class GESTCPClient implements Runnable {

    private Socket socket;
    private Thread thread;
    private int port;
    private List<GESTCPMessageListener> messageListenerList;

    public GESTCPClient(int port){
        this.port = port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void connect(String serverIp, int serverPort) throws IOException {
        socket = new Socket();
        socket.bind(new InetSocketAddress(port));
        socket.connect(new InetSocketAddress(serverIp, serverPort));

        thread = new Thread(this);
        thread.start();
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (socket.isConnected()){
                String str = reader.readLine();
                if(str != null){
                    for(GESTCPMessageListener listener : messageListenerList){
                        listener.onMessageReceive(socket, str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Socket socket, String message) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        pw.println(message);
        pw.flush();
        pw.close();
    }

    public void addMessageListener(GESTCPMessageListener listener){
        messageListenerList.add(listener);
    }

    public void close(){
        //TODO fermer les threads et les sockets (Socket et Server ServerSocket)
    }
}
