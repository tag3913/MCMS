/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thom
 */
public class CreateConnection {
    
    private static Connection con = null;
    
    public  CreateConnection(){
        try {
            establishDatabaseConnection();
        } catch (SQLException ex) {
            System.out.print("Unable to establish Connection");
            Logger.getLogger(CreateConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Create connection to tglenist server
    private static void establishDatabaseConnection() throws SQLException{
        try{
            String strDbUser = "wight18User";       // database loging username
            String strDbPassword = "wight18Password"; // database login password
            String dbIp = "192.168.1.24";       // ip mysql server

            Class.forName("com.mysql.jdbc.Driver");
            //establish connection 
            con = DriverManager.getConnection("jdbc:mysql://" + dbIp + "/wight18?useLegacyDatetimeCode=false&serverTimezone=UTC", strDbUser,
                    strDbPassword);
            
        }
        catch (ClassNotFoundException | SQLException e) {
                System.out.print("connection failed");
                e.printStackTrace();
        }
            
        System.out.print("connection acheived");
    }
    
    
    //old establish connection for connecting to SUNY Oswego server via SSH
    private static void establishConnection() throws SQLException{
        try {
            String strSshUser = "tglenist"; // SSH loging username
            String strSshPassword = "123wednesday"; // SSH login password
            String strSshHost = "pi.cs.oswego.edu"; // hostname or ip or
                                                            // SSH server
            int nSshPort = 22; // remote SSH host port number
            String strRemoteHost = "pi"; // hostname of server w/database
            int nLocalPort = 3366; // local port number use to bind SSH tunnel
            int nRemotePort = 3306; // remote port number of your database
            String strDbUser = "wight18"; // database loging username
            String strDbPassword = "wight2018"; // database login password

            doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort,
                    nRemotePort);

            Class.forName("com.mysql.jdbc.Driver");
            //establish connection 
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + nLocalPort + "/wight18?useLegacyDatetimeCode=false&serverTimezone=UTC", strDbUser,
                    strDbPassword);
            
        }
        catch (JSchException | ClassNotFoundException | SQLException e) {
                System.out.print("connection failed");
                e.printStackTrace();
        }
            
        System.out.print("connection acheived");
    }
    
    private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
            String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
        final JSch jsch = new JSch();
        Session session = jsch.getSession(strSshUser, strSshHost, 22);
        session.setPassword(strSshPassword);

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }
    
    public Connection getConnection(){
        return con;
    }
}
