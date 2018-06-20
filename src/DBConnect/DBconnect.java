/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



/**
 *
 * @author thom
 */
public class DBconnect extends Application{
    
    @Override 
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void main(String [] args){
        launch(args);
        /*
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

            DBconnect.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort,
                    nRemotePort);

            Class.forName("com.mysql.jdbc.Driver");
            //establish connection 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:" + nLocalPort + "/wight18?useLegacyDatetimeCode=false&serverTimezone=UTC", strDbUser,
                    strDbPassword);
            
            //test statement
            Statement stmt = con.createStatement();
            String sql;
            sql = "SELECT * FROM Staff";
            ResultSet rs = stmt.executeQuery(sql);
            
            //rip info from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("StaffID");
                String fname = rs.getString("FirstName");
                String lname = rs.getString("LastName");
                String title = rs.getString("Title");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", First Name: " + fname);
                System.out.print(", Last Name: " + lname);
                System.out.println(", Title: " + title);
            }
            
            //close connection 
            con.close();
            System.out.print("connection acheived");
            
            
        } catch (Exception e) {
            System.out.print("connection failed");
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
*/
            
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
    
    
}

