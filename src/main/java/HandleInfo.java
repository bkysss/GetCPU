import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class HandleInfo {
    public static CPUUsageInfo HandleCPUInfo(String pname,double usage){
        String ip=GetCPUInfo.ip;
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
        String date=dateFormat.format(calendar.getTime());
        CPUUsageInfo cpuUsageInfo;
        if(GetCPUInfo.cpuUsageInfoMap.containsKey(date+ip+pname)){ //若当日该进程已被记录,修改对象信息
            cpuUsageInfo=GetCPUInfo.cpuUsageInfoMap.get(date+ip+pname);
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
            cpuUsageInfo=new CPUUsageInfo(pname,usage,1,usage);
            GetCPUInfo.cpuUsageInfoMap.put(date+ip+pname,cpuUsageInfo);
        }
        return cpuUsageInfo;
    }

    public static void UpdateDaily(String str,String ip) throws ClassNotFoundException, SQLException { //更新当日服务器进程CPU使用情况

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/SmartController";
        Connection conn = DriverManager.getConnection(url, "root", "123456");
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
        String date=dateFormat.format(calendar.getTime());
        System.out.println(date);
        System.out.println("/"+ip+"/");
        try(PreparedStatement ps= conn.prepareStatement("update Daily set ServCPU=? where DDate=? and DIPAddr=?")){
            ps.setObject(1,str);
            ps.setObject(2,date);
            ps.setObject(3,ip);
            int n= ps.executeUpdate();
        }
        conn.close();
    }
}
