/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

/**
HelperFunctions is a class provided to reduce repeat code
 */
public class HelperFunctions {
    
    //gets specific id value from selected string returns -1 if no selected value given
    public static int getIdValue(String selectedValue, int idPos, JLabel warningLabel){
        if(selectedValue != null){
            String[] splitStr = selectedValue.split("\\s+");
            int id = Integer.parseInt(splitStr[idPos]);
            return id;
        }
        else{
            if(warningLabel != null){
                warningLabel.setText("No Value Selected");
            }
        }
        return -1;
    }
    
    //fills provided list with values in array provided
    public static void fillList(String[] items, JList list){
        DefaultListModel dlm = new DefaultListModel();
        for(int i = 0; i < items.length; i++){
            dlm.addElement(items[i]);
        }
        list.setModel(dlm);
    }
    
    //overloaded to apply to array of ints
    //fills provided list with values in array provided
    public static void fillList(int[] items, JList list){
        DefaultListModel dlm = new DefaultListModel();
        for(int i = 0; i < items.length; i++){
            dlm.addElement(items[i]);
        }
        list.setModel(dlm);
    }
}
