package ServerAPP;


import java.awt.Image;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Herik
 */
public class Pregunta {
    int id;
    String path;
    boolean show;
    
    public Pregunta(int ID, String PATH){
        this.id = ID;
        this.path = PATH;
        this.show = false;
    }
}
