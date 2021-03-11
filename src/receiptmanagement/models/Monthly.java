package receiptmanagement.models;

import java.util.Date;

public class Monthly {
    
    private int id, grade;
    private String fn,mn,ln,gender,sec;
    private float remaining;
    private boolean sept,oct,nov,dece,jan,feb,mar,apr,may,jun,jul,aug;

    public Monthly(int id, int grade, String fn, String mn, String ln, String gender, String sec, float remaining, boolean sept, boolean oct, boolean nov, boolean dece, boolean jan, boolean feb, boolean mar, boolean apr, boolean may, boolean jun, boolean jul, boolean aug) {
        this.id = id;
        this.grade = grade;
        this.fn = fn;
        this.mn = mn;
        this.ln = ln;
        this.gender = gender;
        this.sec = sec;
        this.remaining = remaining;
        this.sept = sept;
        this.oct = oct;
        this.nov = nov;
        this.dece = dece;
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public boolean isSept() {
        return sept;
    }

    public void setSept(boolean sept) {
        this.sept = sept;
    }

    public boolean isOct() {
        return oct;
    }

    public void setOct(boolean oct) {
        this.oct = oct;
    }

    public boolean isNov() {
        return nov;
    }

    public void setNov(boolean nov) {
        this.nov = nov;
    }

    public boolean isDece() {
        return dece;
    }

    public void setDece(boolean dece) {
        this.dece = dece;
    }

    public boolean isJan() {
        return jan;
    }

    public void setJan(boolean jan) {
        this.jan = jan;
    }

    public boolean isFeb() {
        return feb;
    }

    public void setFeb(boolean feb) {
        this.feb = feb;
    }

    public boolean isMar() {
        return mar;
    }

    public void setMar(boolean mar) {
        this.mar = mar;
    }

    public boolean isApr() {
        return apr;
    }

    public void setApr(boolean apr) {
        this.apr = apr;
    }

    public boolean isMay() {
        return may;
    }

    public void setMay(boolean may) {
        this.may = may;
    }

    public boolean isJun() {
        return jun;
    }

    public void setJun(boolean jun) {
        this.jun = jun;
    }

    public boolean isJul() {
        return jul;
    }

    public void setJul(boolean jul) {
        this.jul = jul;
    }

    public boolean isAug() {
        return aug;
    }

    public void setAug(boolean aug) {
        this.aug = aug;
    }
    
    
}
