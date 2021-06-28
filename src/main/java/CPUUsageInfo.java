public class CPUUsageInfo {
    private String pname;   //进程名
    private double maxUsage;//该进程CPU使用率最大值
    private int recordTime;//该进程被记录次数
    private double sumUsage; //该进程CPU使用率记录总和

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(double maxUsage) {
        this.maxUsage = maxUsage;
    }

    public int getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(int recordTime) {
        this.recordTime = recordTime;
    }

    public double getSumUsage() {
        return sumUsage;
    }

    public void setSumUsage(double sumUsage) {
        this.sumUsage = sumUsage;
    }

    public CPUUsageInfo(String pname, double maxUsage, int recordTime, double sumUsage) {
        this.pname = pname;
        this.maxUsage = maxUsage;
        this.recordTime = recordTime;
        this.sumUsage = sumUsage;
    }
}
