import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HandleInfo {
    public static void HandleCPUInfo(String pname,double usage){
        String ip="192.168.154.133";
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
        String date=dateFormat.format(calendar.getTime());
        if(GetCPUInfo.cpuUsageInfoMap.containsKey(date+ip+pname)){ //若当日该进程已被记录,修改对象信息
            CPUUsageInfo cpuUsageInfo=GetCPUInfo.cpuUsageInfoMap.get(date+ip+pname);
            double maxUsage=cpuUsageInfo.getMaxUsage();
            int recordTime=cpuUsageInfo.getRecordTime();
            double sumUsage=cpuUsageInfo.getSumUsage();
            if(maxUsage<usage){
                maxUsage=usage;
            }
            recordTime++;
            sumUsage+=usage;
            cpuUsageInfo.setMaxUsage(maxUsage);
            cpuUsageInfo.setRecordTime(recordTime);
            cpuUsageInfo.setSumUsage(sumUsage);
            GetCPUInfo.cpuUsageInfoMap.put(date+ip+pname,cpuUsageInfo);
        }
        else { //当日该进程未被记录
            CPUUsageInfo cpuUsageInfo=new CPUUsageInfo(pname,usage,1,usage);
            GetCPUInfo.cpuUsageInfoMap.put(date+ip+pname,cpuUsageInfo);
        }
    }
}
