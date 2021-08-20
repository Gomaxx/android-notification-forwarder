package cc.eoma.nf.sender;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketSender {
    public static void send(String ip, Integer port, String message) {
        try (Socket socket = new Socket(ip, port); OutputStream outputStream = socket.getOutputStream()) {
            outputStream.write((message + "\n").getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
