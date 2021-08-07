import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimerTask;

public class Task extends TimerTask {
    private final String SERVER_IP="localhost";
    public void run() {
        String sqlStr="";
        try{
            String[] cmd=new String[] {"/bin/sh","-c","top -n 1 -b|awk '{print $12 \"\\t\" $9}'|sed -n '8,12p'"};
            Process proc = Runtime.getRuntime().exec(cmd);
            //获取脚本执行的得到的结果并缓存
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String str,pname,cpuUsage;
            //读取数据
            while ((str = reader.readLine()) != null) {
                String[] st=str.split("\t");
                pname=st[0];
                cpuUsage=st[1];
                CPUUsageInfo cui=HandleInfo.HandleCPUInfo(pname,Double.parseDouble(cpuUsage));
                sqlStr+=pname+"|"+cui.getMaxUsage()+"|"+String.format("%.2f",cui.getSumUsage()/cui.getRecordTime())+";";
            }
            reader.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(sqlStr);

        try {
            Socket socket=new Socket(SERVER_IP,9097);
            try(InputStream is =socket.getInputStream()){
                try(OutputStream os =socket.getOutputStream()){
                    handle(sqlStr,is,os);
                    socket.close();
                }
            }

            System.out.println("disconnected");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            HandleInfo.UpdateDaily(sqlStr);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }

    private static void handle(String str,InputStream input, OutputStream output) throws IOException, InterruptedException {
        var writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("[server] " + reader.readLine());
        System.out.print(">>> "); // 打印提示
        writer.write(str);
        writer.newLine();
        writer.flush();
//        for (;;) {
//
//            //Thread.sleep(500);
//
//            break;
//
//        }
    }

}
