/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientep;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author remojosefiorentinocasadiego
 */

public class tiempo {
    
    public void delay(){
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}


