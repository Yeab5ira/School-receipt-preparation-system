package receiptmanagement.models;

import java.util.Date;

public class Payment {
    private String Payer,reason,cashier,sec;
    private int grade,payerId,serial;
    private float amount;
    private boolean printed,disabled;
    private Date date;

    public Payment(String Payer, String reason, String cashier, String sec, int grade, int payerId, int serial, float amount, boolean printed, boolean disabled, Date date) {
        this.Payer = Payer;
        this.reason = reason;
        this.cashier = cashier;
        this.sec = sec;
        this.grade = grade;
        this.payerId = payerId;
        this.serial = serial;
        this.amount = amount;
        this.printed = printed;
        this.disabled = disabled;
        this.date = date;
    }

    public String getPayer() {
        return Payer;
    }

    public void setPayer(String Payer) {
        this.Payer = Payer;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
}
