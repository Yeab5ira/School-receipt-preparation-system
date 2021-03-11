package receiptmanagement.services;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentServices {
    
    Connection con = new Database().createCon();
    //add student to database
    public int addStudent(String fName, String mName, String lName, String gender, int grade, String sec){
        String sql = "INSERT INTO students(fn,mn,ln,gender,grade,sec,remaining,left_school) VALUES(?,?,?,?,?,?,0,0)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, fName);
            ps.setString(2, mName);
            ps.setString(3, lName);
            ps.setString(4, gender);
            ps.setInt(5, grade);
            ps.setString(6, sec);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentServices.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return 0;
    }
    
    //edit student
    public void editStudent(int id, String fn, String mn, String ln, String gender, int grade, String sec){
        String sql = "UPDATE students SET fn=?, mn=?, ln=?, gender=?, grade=?, sec=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fn);
            ps.setString(2, mn);
            ps.setString(3, ln);
            ps.setString(4, gender);
            ps.setInt(5, grade);
            ps.setString(6, sec);
            ps.setInt(7, id);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //delete student
    public void deleteStudent(int id){
        String sql = "DELETE FROM students WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
