package cn.edu.fzu.sm.wuweida.sms.bean;

public class AnalyzeResult {

    private  String studentId;
    private String studentName;
    private String sumScore;	//学生总成绩
    private String avgScore;	//学生平均成绩


    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getSumScore() {
        return sumScore;
    }
    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }
    public String getAvgScore() {
        return avgScore;
    }
    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }
}
