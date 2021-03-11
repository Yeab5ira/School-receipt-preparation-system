package receiptmanagement.services;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import receiptmanagement.models.Payment;

public class PaymentServices {
    public PaymentServices(){
        
    }
    Connection con = new Database().createCon();
    
    //receipt preparing method
    public void prepareReceipt(int sId, String payer, int grade, String sec, LocalDate date, String reason, float amount, String cashier){
        String sql = "INSERT INTO payments(sId,payer,grade,sec,date,reason,amount,cashier) VALUES(?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sId);
            ps.setString(2, payer);
            ps.setInt(3, grade);
            ps.setString(4, sec);
            ps.setDate(5, Date.valueOf(date));
            ps.setString(6, reason);
            ps.setFloat(7, amount);
            ps.setString(8, cashier);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    //fetch all payments made 
    public ArrayList<Payment> fetchReport(){
        //create a list to store the payment models
        ArrayList<Payment> alPayments = new ArrayList<>();
        //fetch payments from database
        String sql = "SELECT * FROM payments";
        
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while(rs.next()){
                //loop through the result set and add a payment model
                alPayments.add(new Payment(rs.getString("payer"), rs.getString("reason"),rs.getString("cashier"), rs.getString("sec"), rs.getInt("grade"), rs.getInt("sId"), rs.getInt("serial"),  rs.getFloat("amount"), rs.getBoolean("printed"), rs.getBoolean("disabled"), rs.getDate("date") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alPayments;
    }
    
    //fetch payments made from a grade
    public ArrayList<Payment> fetchReport(int grade){
        //create a list to store the payments 
        ArrayList<Payment> alPayments = new ArrayList<>();
        //fetch payments from database
        String sql = "SELECT * FROM payments WHERE grade = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, grade);
            ResultSet rs = ps.executeQuery();
            //loop through the result set and add the payment model to the list
            while(rs.next()){
                alPayments.add(new Payment(rs.getString("payer"), rs.getString("reason"),rs.getString("cashier"), rs.getString("sec"), rs.getInt("grade"), rs.getInt("sId"), rs.getInt("serial"),  rs.getFloat("amount"), rs.getBoolean("printed"), rs.getBoolean("disabled"), rs.getDate("date") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alPayments;
    }
    //fetch payments made within the selected date
    public ArrayList<Payment> fetchReport(Date date){
        ArrayList<Payment> alPayments = new ArrayList<>();
        
        String sql = "SELECT * FROM payments WHERE date = ?";
        
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                alPayments.add(new Payment(rs.getString("payer"), rs.getString("reason"),rs.getString("cashier"), rs.getString("sec"), rs.getInt("grade"), rs.getInt("sId"), rs.getInt("serial"),  rs.getFloat("amount"), rs.getBoolean("printed"), rs.getBoolean("disabled"), rs.getDate("date") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alPayments;
    }
    //fetch all payments made with the given grade and date
    public ArrayList<Payment> fetchReport(int grade, Date date){
        ArrayList<Payment> alPayments = new ArrayList<>();
        
        String sql = "SELECT * FROM payments WHERE date = ? AND grade = ?";
        
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setInt(2, grade);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                alPayments.add(new Payment(rs.getString("payer"), rs.getString("reason"),rs.getString("cashier"), rs.getString("sec"), rs.getInt("grade"), rs.getInt("sId"), rs.getInt("serial"),  rs.getFloat("amount"), rs.getBoolean("printed"), rs.getBoolean("disabled"), rs.getDate("date") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alPayments;
    }
    //search payments 
    public ArrayList<Payment> searchReport(String name){
        ArrayList<Payment> alPayments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        if(!name.isEmpty()){
            sql = "SELECT * FROM payments WHERE payer LIKE '%"+name+"%'";
        } 
        
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while(rs.next()){
                alPayments.add(new Payment(rs.getString("payer"), rs.getString("reason"),rs.getString("cashier"), rs.getString("sec"), rs.getInt("grade"), rs.getInt("sId"), rs.getInt("serial"),  rs.getFloat("amount"), rs.getBoolean("printed"), rs.getBoolean("disabled"), rs.getDate("date") ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alPayments;
    }

    
    //switch void receipt method
    public void disableReceipt(String serial) {
            //toggle the disabled status 
            String sql = "UPDATE payments SET disabled=!disabled WHERE serial=?";
            
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(serial));
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMonthlyPaymentStat(int studentId, LinkedList<String> selectedMonths) {
        
        selectedMonths.forEach(month -> {
            String sql = "UPDATE monthly SET "+month+" = 1 WHERE sId="+studentId+"";
            try {
                new Database().createCon().createStatement().executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
}
