import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimerTask;

public class Task extends TimerTask {
    public void run() {
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
                HandleInfo.HandleCPUInfo(pname,Double.parseDouble(cpuUsage));
            }
            reader.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}