package receiptmanagement.models;

public class Payment {
    private String Payer,date,reason,cashier,sec;
    private int id,grade;
    private float amount;
    private boolean printed;

    public Payment(String Payer, String date, String reason, String cashier, String sec, int id, int grade, float amount, boolean printed) {
        this.Payer = Payer;
        this.date = date;
        this.reason = reason;
        this.cashier = cashier;
        this.sec = sec;
        this.id = id;
        this.grade = grade;
        this.amount = amount;
        this.printed = printed;
    }

    public String getPayer() {
        return Payer;
    }

    public void setPayer(String Payer) {
        this.Payer = Payer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isPrinted() {
        return printed;
    }

    public void setPrinted(boolean printed) {
        this.printed = printed;
    }

    
    
    
}
