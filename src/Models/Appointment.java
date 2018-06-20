package Models;


import MainPackage.QueryExecuter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*QueryExecuter.java:25
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thom
 */
public class Appointment {
    int appId;
    String appDate;
    String appTime;
    
    public Appointment(int id, String date, String time){
        appId = id;
        appDate = date;
        appTime = time; 
    }
    
    public static String[] getPhysApps(int physId){
        
        String physAppsQuery = "SELECT a.AppointmentID, a.AppointmentDate, a.AppointmentTime, p.PatientID, p.FirstName, p.LastName\n" +
"FROM Appointments a JOIN AppointmentLine l JOIN Patients p\n" +
"WHERE a.AppointmentID = l.AppointmentID AND l.PatientID = p.PatientID AND l.StaffID = " + physId + ";";
        
        ResultSet apps = QueryExecuter.runQuery(physAppsQuery);
        String[] appointments = new String[QueryExecuter.getRowCount(apps)]; 
        String idspacing;
        String spacing = "     ";
        
        int counter = 0;
        try {
            while(apps.next()){
                
                int id = apps.getInt("AppointmentID");
                String time = apps.getString("AppointmentTime");
                String date = apps.getString("AppointmentDate");
                String fname = apps.getString("FirstName");
                String lname = apps.getString("LastName");
                if(id > 9){
                    idspacing = " ";
                }
                else
                    idspacing = "   ";
                appointments[counter] = id + idspacing + time + spacing + date + spacing + fname + " " + lname; 
                counter++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return appointments;
    }
    
    
    public static String getAppInfo(int appId){
        String appQuery = "SELECT a.AppointmentID, a.AppointmentDate, a.AppointmentTime, p.PatientID, p.FirstName, p.LastName, s.StaffID, s.FirstName, s.LastName, s.SignedIn\n" +
                          "FROM Patients p JOIN Staff s JOIN Appointments a JOIN AppointmentLine\n" +
                          "WHERE p.PatientID = AppointmentLine.PatientID AND a.AppointmentID = AppointmentLine.AppointmentID AND s.StaffID = AppointmentLine.StaffID\n" +
                          "AND a.AppointmentID = " + appId +";";
        ResultSet rs = QueryExecuter.runQuery(appQuery);
        String appInfo = "";
        try {
            while(rs.next()){
                appInfo += "Appointment Information-\n";
                appInfo += "Appointment Id: " + rs.getInt("a.AppointmentID") + "\n";
                appInfo += "Appointment Date: " + rs.getString("a.AppointmentDate") + "\n";
                appInfo += "Appointment Time: " + rs.getString("a.AppointmentTime") + "\n\n\n";
                
                appInfo += "Patient Information-\n";
                appInfo += "Patient Id: " + rs.getInt("p.PatientID") + "\n";
                appInfo += "Patient Firstname: " + rs.getString("p.FirstName") + "\n";
                appInfo += "Patient Lastname: " + rs.getString("p.LastName") + "\n\n\n";
                
                appInfo += "Primary Physician Information - \n";
                appInfo += "Physician Id: " + rs.getString("s.StaffID") + "\n";
                appInfo += "Physician Firstname: " + rs.getString("s.FirstName") + "\n";
                appInfo += "Physician Lastname: " + rs.getString("s.LastName") + "\n";
                appInfo += "Signed In: " + rs.getString("s.SignedIn") + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appInfo;
    }
    
    public static String[] getAppointments(){
        String appQuery = "SELECT a.AppointmentID, a.AppointmentTime, a.AppointmentDate, p.FirstName, p.LastName FROM Patients p JOIN AppointmentLine l join Appointments a \n" +
"where p.PatientID = l.PatientID AND l.AppointmentID = a.AppointmentID AND a.AppointmentComplete = 'No' AND p.PatientID NOT IN \n" +
"(SELECT PatientID FROM Rooms JOIN Patients WHERE Rooms.RoomPatient = Patients.PatientID);";
        
        ResultSet apps = QueryExecuter.runQuery(appQuery);
        String[] appointments = new String[QueryExecuter.getRowCount(apps)]; 
        String idspacing;
        String spacing = "     ";
        
        int counter = 0;
        try {
            while(apps.next()){
                
                int id = apps.getInt("AppointmentID");
                String time = apps.getString("AppointmentTime");
                String date = apps.getString("AppointmentDate");
                String fname = apps.getString("FirstName");
                String lname = apps.getString("LastName");
                if(id > 9){
                    idspacing = "   ";
                }
                else
                    idspacing = "     ";
                appointments[counter] = id + idspacing + time + spacing + date + spacing + fname + " " + lname; 
                counter++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return appointments;
    }
    
}
