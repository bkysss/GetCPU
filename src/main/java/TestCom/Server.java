package TestCom;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8097); // 监听指定端口
        System.out.println("server is running...");
        Socket sock = ss.accept();
        System.out.println("connected from " + sock.getRemoteSocketAddress());
        new Thread(new Handler(sock)).start();

    }
}

class Handler implements Runnable {
    Socket sock;

    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream()) {
            try (OutputStream output = this.sock.getOutputStream()) {
                handle(input, output);
            }
        } catch (Exception e) {

        }finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
//        writer.write("shutdown");
//        writer.flush();
        String s = reader.readLine();
        if (s.equals("shutdown")) {

            //System.out.println(s);

            writer.write("ok");
            writer.flush();

        }

    }
}