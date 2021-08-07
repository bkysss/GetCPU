import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class Server{

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9097);
        for(;;){
            Socket socket=serverSocket.accept();
            new Thread(new Handler(socket)).start();
        }
    }
}

class Handler implements Runnable{
    Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(InputStream inputStream=socket.getInputStream()){
            try(OutputStream outputStream=socket.getOutputStream()){
                handle(inputStream,outputStream);
            }
        }catch (Exception e){
            try {
                this.socket.close();
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    public void handle(InputStream is, OutputStream os) throws IOException, SQLException, ClassNotFoundException {
        var reader=new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        var writer=new BufferedWriter(new OutputStreamWriter(os,StandardCharsets.UTF_8));
        for(;;){
            String s=reader.readLine();
            if(!s.equals("null")){
                System.out.println(socket.getRemoteSocketAddress()+": "+s);
                SocketAddress ip=socket.getRemoteSocketAddress();
                String str=ip.toString();
                System.out.println(str.substring(1,str.indexOf(':')));
                HandleInfo.UpdateDaily(s,str.substring(1,str.indexOf(':')));
            }

//            writer.write("ok");
//            writer.flush();
        }
    }
}
