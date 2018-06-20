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
public class Patient {
     
     public static String[] getPatientInfo(int pId){
        int illness = 0;
        int symptom = 1;
        int medication = 2;
        int allergy = 3;
        
        String[] pInfo = new String[4];
        
        String pinfoQuery = "SELECT i.IllnessName, s.SymptomName, p.Medications, p.Allergies\n" +
"FROM Patients p JOIN PatientStatus ps JOIN Symptoms s JOIN Illnesses i\n" +
"WHERE p.PatientID = ps.PatientID AND ps.PatientIllness = i.IllnessID AND ps.PatientSymptom = s.SymptomID\n" +
"AND p.PatientID = " + pId + ";";
        
        
        ResultSet rs = QueryExecuter.runQuery(pinfoQuery);
        Boolean hasIllness = true;
        Boolean hasSymptoms = true;
        
        try {
            if (!rs.isBeforeFirst() ) {   
                hasIllness = false;
                String newQuery = "SELECT p.PatientID, p.FirstName, p.LastName, p.Medications, p.Allergies, s.SymptomName \n" +
                                  "FROM Patients p JOIN PatientStatus ps JOIN Symptoms s\n" +
                                  "WHERE p.PatientID = ps.PatientID AND ps.PatientSymptom = s.SymptomID AND p.PatientID = " + pId + ";"; 
                rs = QueryExecuter.runQuery(newQuery);
            }
            if(!rs.isBeforeFirst() ){
                hasIllness = true;
                hasSymptoms = false;
                String newQuery = "SELECT p.PatientID, p.FirstName, p.LastName, p.Medications, p.Allergies, i.IllnessName \n" +
                                   "FROM Patients p JOIN PatientStatus ps JOIN Illnesses i\n" +
                                   "WHERE p.PatientID = ps.PatientID AND ps.PatientIllness = i.IllnessID AND p.PatientID = " + pId + ";"; 
                rs = QueryExecuter.runQuery(newQuery);
            }
            if (!rs.isBeforeFirst() ) {    
                hasIllness = false;
                hasSymptoms = false;
                String newQuery = "SELECT p.PatientID, p.FirstName, p.LastName, p.Medications, p.Allergies \n" +
                                  "FROM Patients p  \n" +
                                  "WHERE p.PatientID = " + pId + ";"; 
                rs = QueryExecuter.runQuery(newQuery);
            } 
            while(rs.next()){
                pInfo[medication] = rs.getString("p.Medications");
                pInfo[allergy] = rs.getString("p.Allergies");
                if(hasIllness){
                    pInfo[illness] = rs.getString("i.IllnessName");
                }
                if(hasSymptoms){
                    pInfo[symptom] = rs.getString("s.SymptomName");
                }
            }
        }catch (SQLException ex) {
            System.out.print("Somethings Wrong");
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return pInfo;
         
     }
    
}
