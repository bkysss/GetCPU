import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class GetCPUInfo {
    static Map<String,CPUUsageInfo> cpuUsageInfoMap;
    static String ip="192.168.154.133";

    public static void main(String[] args) throws Exception {
        cpuUsageInfoMap=new HashMap<>();
        new GetCPUInfo();
    }
    private static final long PERIOD_TIME = 2 * 60 * 1000;
    public GetCPUInfo() throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0); //凌晨0点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        Timer timer = new Timer();
        Task task = new Task();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task,date,PERIOD_TIME);
    }
}
