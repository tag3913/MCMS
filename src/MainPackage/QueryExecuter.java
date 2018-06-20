package MainPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *@author thom
 * Query Executer is a helper class used by models to retrieve and update information on the Database(DB)
 */
public class QueryExecuter {
    
    //Connection used in statements to be executed on the DB
    
    //static method for running Select statements on the DB
    public static ResultSet runQuery(String statement){
        Statement stmt;
        try {
            stmt = Login.con.createStatement();
            ResultSet rs = stmt.executeQuery(statement);
            return rs;
        } catch (SQLException ex) {
            //should put something else here to the screen
            Logger.getLogger(QueryExecuter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    //static method for running insert/Update statements on the DB
    public static int runUpdate(String statement){
        Statement stmt;
        try {
            stmt = Login.con.createStatement();
            int result = stmt.executeUpdate(statement);
            return result;
        } catch (SQLException ex) {
            //should put something else here to the screen
            Logger.getLogger(QueryExecuter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }
    
    //returns the number of rows in a result set
    public static int getRowCount(ResultSet resultSet) {
        if (resultSet == null) {
            return 0;
        }
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                resultSet.beforeFirst();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
        return 0;
    }
}
