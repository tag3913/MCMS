/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import MainPackage.QueryExecuter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thom
 */
public class Rooms {
    
    //gets the list of currently checked in patients
    public static String[] getCheckedIn(){
        String checkedInQuery = "SELECT p.PatientID, p.FirstName, p.LastName, r.RoomID\n" +
                                "FROM Patients p JOIN Rooms r\n" +
                                "WHERE p.PatientID = r.RoomPatient;";
        ResultSet chdIn = QueryExecuter.runQuery(checkedInQuery);
        String[] checkedIn = new String[QueryExecuter.getRowCount(chdIn)];
        int counter = 0;
        try {
            while(chdIn.next()){
                int pId = chdIn.getInt("p.PatientId");
                String fname = chdIn.getString("p.FirstName");
                String lname = chdIn.getString("p.LastName");
                int roomId = chdIn.getInt("r.RoomID");
                 
                checkedIn[counter] = "PID: " + pId + "     Name: " + fname + " "  + lname + "     RoomID: " + roomId;
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Rooms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkedIn;
    }
     
    //checks a patient out of their appointment. 
    public static void CheckOut(int patientId){
        String chkoutAppQuery = "UPDATE Appointments a JOIN AppointmentLine l\n" +
                                "SET a.AppointmentComplete = 'Yes' \n" +
                                "WHERE l.AppointmentID = a.AppointmentID AND l.PatientID = " + patientId + ";";
        QueryExecuter.runUpdate(chkoutAppQuery);
        
        String chkoutRoomQuery = "UPDATE Rooms\n" +
                                 "SET RoomPatient = null\n" +
                                 "WHERE RoomPatient =" + patientId + ";";
        QueryExecuter.runUpdate(chkoutRoomQuery);
    }
    
    //returns the available rooms that do not currently have a patient in them
    public static int[] getAvailableRooms(){
        String roomQuery = "SELECT RoomID\n" +
                        "FROM Rooms\n" +
                        "WHERE RoomType = 'Patient Care' AND RoomPatient IS null;";
        
        ResultSet appRooms = QueryExecuter.runQuery(roomQuery);
        int[] rooms = new int[QueryExecuter.getRowCount(appRooms)];
        
        int counter = 0;
        try {
            while(appRooms.next()){
                rooms[counter] = appRooms.getInt("RoomID");
                counter++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rooms;
    }
    
}
