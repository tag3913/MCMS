package Models;

import MainPackage.QueryExecuter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
Performs all queries for accessing and returning employee data.
 */

/**
 *
 * @author thom
 */
public class Employee {
    
    int id;
    String firstName;
    String lastName;
    String username;
    
    public Employee(int staffId, String fname, String lname){
        id = staffId;
        firstName = fname;
        lastName = lname;
    }
    
    public static ResultSet getEmployee(String username){
        String loginQuery = "SELECT * FROM Staff HAVING Username='" + username + "'";
        ResultSet rs = QueryExecuter.runQuery(loginQuery);
        return rs;
    }
    
    //clocks in the employee that has signed in
    public void ClockIn(){
        String clockInQuery = "UPDATE Staff\n" +
                              "SET SignedIn = 'Yes'\n" +
                              "WHERE StaffID = " + id + ";";
        QueryExecuter.runUpdate(clockInQuery);
    }
    
    //clocks out the employee that is signed in
    public void ClockOut(){
        String clockOutQuery = "UPDATE Staff\n" +
                              "SET SignedIn = 'No'\n" +
                              "WHERE StaffID = " + id + ";";
        QueryExecuter.runUpdate(clockOutQuery);
    }
    
    //getters and setters
    public void setFirstName(String fname){
        firstName = fname;
    }
    
    public void setLastName(String lname){
        lastName = lname;
    }
    
    public String getName(){
        return firstName + " " + lastName;
    }
    
    public int getId(){
        return this.id;
    }
    
    
    //returns the info for the provided employee from that employees Id number
    public static String getEmpInfo(int empId){
        String empQuery = "SELECT StaffID, FirstName, LastName, Username, Title, email\n" +
                          "FROM Staff\n" +
                          "WHERE StaffID = " + empId + ";";
        ResultSet rs = QueryExecuter.runQuery(empQuery);
        String empInfo = "";
        try {
            while(rs.next()){
                empInfo += "Employee Information-\n";
                empInfo += "Employee Id: " + rs.getInt("StaffID") + "\n\n";
                empInfo += "First name: " + rs.getString("FirstName") + "\n\n";
                empInfo += "Last name: " + rs.getString("LastName") + "\n\n";
                empInfo += "Email: " + rs.getString("email") + "\n\n";
                empInfo += "Username: " + rs.getString("Username") + "\n\n";
                empInfo += "Title: " + rs.getString("Title") + "\n\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empInfo;
    }
    
    //Returns all employees that may be clocked in or out depending on parameter
    public static String[] getEmployees(boolean clockedIn){
        String empQuery;
        if(clockedIn)
            empQuery = "SELECT StaffID, FirstName, LastName\n" +
                       "FROM Staff\n" +
                       "WHERE Signedin = 'Yes';";
        else
            empQuery = "SELECT StaffID, FirstName, LastName\n" +
                       "FROM Staff\n" +
                       "WHERE Signedin = 'No';";
        
        ResultSet emps = QueryExecuter.runQuery(empQuery);
        String[] employees = new String[QueryExecuter.getRowCount(emps)]; 
        String idspacing;
        
        int counter = 0;
        try {
            while(emps.next()){
                
                int id = emps.getInt("StaffID");
                String fname = emps.getString("FirstName");
                String lname = emps.getString("LastName");
                if(id > 9){
                    idspacing = "  ";
                }
                else
                    idspacing = "    ";
                employees[counter] = id + idspacing + fname + " " + lname; 
                counter++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }
    
    //Returns all patients for a given physician ID
    public static String[] getPatients(int physId){
        String patQuery = "SELECT p.PatientID, p.FirstName, p.LastName, p.Age, p.Gender\n" +
                          "FROM Patients p JOIN Staff s\n" +
                          "WHERE p.PrimaryPhysician = s.StaffID AND s.StaffID = " + physId + ";";
        ResultSet pats = QueryExecuter.runQuery(patQuery);
        String[] patients = new String[QueryExecuter.getRowCount(pats)]; 
        String idspacing;
        int counter = 0;
        try {
            while(pats.next()){  
                int id = pats.getInt("PatientID");
                String fname = pats.getString("FirstName");
                String lname = pats.getString("LastName");
                int age = pats.getInt("Age");
                String gender = pats.getString("Gender");
                if(id > 9){
                    idspacing = " ";
                }
                else
                    idspacing = "   ";
                patients[counter] = id + idspacing + fname + " " + lname + "  " + age + "  " + gender; 
                counter++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return patients;
        
    }
   
}
