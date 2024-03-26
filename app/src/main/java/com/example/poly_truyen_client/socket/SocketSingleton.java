package com.example.poly_truyen_client.socket;

import com.example.poly_truyen_client.api.ConnectAPI;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketSingleton {
    private static SocketSingleton instance;
    private Socket socket;

    private SocketSingleton() {
        try {
            socket = IO.socket(new ConnectAPI().API_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SocketSingleton getInstance() {
        if (instance == null) {
            instance = new SocketSingleton();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

}
