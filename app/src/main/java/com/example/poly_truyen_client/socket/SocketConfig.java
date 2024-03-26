package com.example.poly_truyen_client.socket;

import com.example.poly_truyen_client.api.ConnectAPI;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketConfig {

    private Socket socket;

    public SocketConfig() {
        this.socket = SocketSingleton.getInstance().getSocket();
        socket.connect();
    }


}
