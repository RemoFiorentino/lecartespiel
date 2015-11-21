/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Herik
 */
public class Pregunta {
    String pregunta;
    String respuesta;
    
    public Pregunta(String[] datos){
        this.pregunta = datos[1];
        this.respuesta = datos[2];
    }
}
