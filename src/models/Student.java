package models;

public class Student {
    private int id, grade;
    private String fn,mn,ln,gender,sec;
    private float remaining;
    private boolean leftSchool;

    public Student(int id, int grade, String fn, String mn, String ln, String gender, String sec, float remaining, boolean leftSchool) {
        this.id = id;
        this.grade = grade;
        this.fn = fn;
        this.mn = mn;
        this.ln = ln;
        this.gender = gender;
        this.sec = sec;
        this.remaining = remaining;
        this.leftSchool = leftSchool;
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

    public boolean isLeftSchool() {
        return leftSchool;
    }

    public void setLeftSchool(boolean leftSchool) {
        this.leftSchool = leftSchool;
    }
    
    
}
