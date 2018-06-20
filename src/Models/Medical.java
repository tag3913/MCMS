/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import MainPackage.QueryExecuter;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author thom
 */
public class Medical {
    
    static String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    
    public static void addMedication(int pId, String med){
        String updateMedication = "UPDATE Patients\n" +
                                  "SET Medications = CONCAT(Medications, ' " + med + "')\n" +
                                  "WHERE PatientID = " + pId + ";";
        QueryExecuter.runUpdate(updateMedication);
    }
    
    public static void addAllergy(int pId, String allergy){
        String updateAllergy = "UPDATE Patients\n" +
                               "SET Allergies = CONCAT(Allergies, ' " + allergy + "')\n" +
                               "WHERE PatientID = " + pId + ";";
        QueryExecuter.runUpdate(updateAllergy);
    }
    
    public static void addSymptom(int pId, int SymId){
        String insertSymptom = "INSERT into PatientStatus (PatientID, PatientIllness, PatientSymptom, Date)\n" +
                               "VALUES (" + pId + ", null," + SymId + ", '" + date + "');";
        QueryExecuter.runUpdate(insertSymptom);
    }
    
    public static void addIllness(int pId, int illId){
        String insertIllness = "INSERT into PatientStatus (PatientID, PatientIllness, PatientSymptom, Date)\n" +
                               "VALUES (" + pId + "," + illId + ", null, '" + date + "');";
        QueryExecuter.runUpdate(insertIllness);
    }
    
    public static String getSymptoms(String illName){
        String relatedSymptoms = "Common Symptoms-\n";
        String symQuery;
        symQuery = "SELECT s.SymptomName \n" +
                          "FROM Symptoms s JOIN IllnessLine l JOIN Illnesses i\n" +
                          "WHERE s.SymptomID = l.SymptomID AND i.IllnessID = l.IllnessID AND i.IllnessName = '" + illName + "';";
        ResultSet rs = QueryExecuter.runQuery(symQuery);
        
        try {
            while(rs.next()){
                relatedSymptoms += rs.getString("s.SymptomName") + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medical.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relatedSymptoms;
    }
    
    public static String getIllnesses(String symName){
        String relatedIllnesses = "Related Illnesses-\n";
        
        String symQuery = "SELECT i.IllnessName \n" +
                          "FROM Symptoms s JOIN IllnessLine l JOIN Illnesses i\n" +
                          "WHERE s.SymptomID = l.SymptomID AND i.IllnessID = l.IllnessID AND s.SymptomName = '" + symName + "';";
        ResultSet rs = QueryExecuter.runQuery(symQuery);
        
        try {
            while(rs.next()){
                relatedIllnesses += rs.getString("i.IllnessName") + "\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medical.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relatedIllnesses;
    }
    
    public static String[] getAllIllnesses(){
        String[] ills;
        String illnessesQuery = "SELECT IllnessID, IllnessName\n" +
                                "FROM Illnesses;";
        ResultSet rs = QueryExecuter.runQuery(illnessesQuery);
        ills = new String[QueryExecuter.getRowCount(rs)];
        int counter = 0;
        try {
            while(rs.next()){
                ills[counter] = rs.getInt("IllnessID") + " " + rs.getString("IllnessName");
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medical.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ills;
    }
    
    public static String[] getAllSymptoms(){
        String[] syms;
        String illnessesQuery = "SELECT SymptomID, SymptomName\n" +
                                "FROM Symptoms;";
        ResultSet rs = QueryExecuter.runQuery(illnessesQuery);
        syms = new String[QueryExecuter.getRowCount(rs)];
        int counter = 0;
        try {
            while(rs.next()){
                syms[counter] = rs.getInt("SymptomID") + " " + rs.getString("SymptomName");
                counter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medical.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return syms;
    }
    
}
