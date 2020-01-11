package ges.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GESTCPServer implements Runnable {

    private int port;
    private volatile boolean running;
    private Thread serverThread;
    private ServerSocket serverSocket;
    private Map<Socket, Thread> threadSocketMap;
    private List<GESTCPConnect> connectListenerList;
    private List<GESTCPMessageListener> messageListenerList;

    public GESTCPServer(){
        messageListenerList = new ArrayList<>();
        connectListenerList = new ArrayList<>();
        threadSocketMap = new HashMap<>();
    }

    public GESTCPServer(int port){
        this();
        this.port = port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void listen(){
        serverThread = new Thread(this);
        serverThread.start();
    }

    public void listen(int port){
        setPort(port);
        listen();
    }

    @Override
    public void run() {
        running = true;
        try {
            serverSocket = new ServerSocket(port);
            while (running){
                System.out.println("En attente sur le port " + port);
                Socket socket = serverSocket.accept();
                for(GESTCPConnect cl : connectListenerList){
                    cl.onConnection(socket);
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String str = null;
                            while(socket.isClosed() == false){
                                str = reader.readLine();
                                if(str != null) {
                                    for (GESTCPMessageListener listener : messageListenerList) {
                                        listener.onMessageReceive(socket, str);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                threadSocketMap.put(socket, thread);
            }
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }
    }

    public void sendMessage(Socket socket, String message) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        pw.println(message);
        pw.flush();
        pw.close();
    }

    public void addConnectionListener(GESTCPConnect connect){
        connectListenerList.add(connect);
    }

    public void addMessageListener(GESTCPMessageListener listener){
        messageListenerList.add(listener);
    }

    public void close(){
        //TODO fermer les threads et les sockets (Socket et Server ServerSocket)
    }
}
