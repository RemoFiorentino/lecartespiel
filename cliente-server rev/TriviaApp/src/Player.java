
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Herik
 */
public class Player extends Thread {
    static int currentP = 1;
    Socket connection; 
    DataInputStream entrada;
    DataOutputStream salida;
    int playerID;
    int puntaje;
    String playernom;
    HashMap<Integer,Player> players;
    HashMap<String,ArrayList<Pregunta>> preguntas;
    boolean suspended = true; 
    boolean respuesta = true; 

    public Player( Socket sc, int ID, HashMap<Integer,Player> pls,HashMap<String,ArrayList<Pregunta>> prg){
        
        playerID = ID; 
        connection = sc; 
        players=pls;
        preguntas=prg;
        try{
            salida = new DataOutputStream(connection.getOutputStream());
            salida.flush();
            entrada = new DataInputStream(connection.getInputStream());
        }catch(IOException ioex){
            ioex.printStackTrace();
        }
    }
    @Override
    public void run(){
        while(true){
            try{
                String mensaje = (String) entrada.readUTF();
                if(!mensaje.startsWith("-", 1)){
                    enviarTodos(mensaje,false);
                }else{
                    String[] func = mensaje.split("-");
                    switch(func[0]){
                        case "n":
                            playernom=func[1];
                        break;
                        case "p":
                            if(func[1].equals("true")){
                                puntaje++;
                                actualizarP();
                                enviarTodos(playernom+" contesto correctamente", false);
                            }else{
                                siguiente();
                            }
                        break;
                        case "q":
                            String[] msj = mensaje.split("-");
                            String categ = msj[1];
                            int n = Integer.parseInt(msj[2]);
                            Pregunta p = preguntas.get(categ).get(n);
                            players.get(playerID).enviarMensaje("Pregunta:-"+p.pregunta+"-"+p.respuesta);
                        break;
                        case "g":
                            enviarTodos("ganador:-"+func[1],true);
                        break;    
                    }
                }
            }
            catch(IOException ioex){
                break;
            }
        }
        players.remove(this);
    }
    public void actualizarP(){
        String p="Puntaje:";
        for (Integer i : players.keySet()) {
            Player pl = players.get(i);
            p=p+"-"+pl.playernom+": "+pl.puntaje;
        }
        enviarTodos(p,true);
    }
    
    public void siguiente(){
        if(currentP<players.size()){
            currentP++;
        }else{
            currentP=1;
        }
        System.out.println("next player: "+currentP);
        Player p = players.get(currentP);
        p.enviarMensaje("Turno:-"+p.playernom);
        enviarTodos("Es el turno de "+p.playernom,false);
    }
    public void enviarMensaje(String mensaje){
        try {
            salida.writeUTF(mensaje);
            salida.flush();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviarTodos(String mensaje,boolean function){
        for (Integer i : players.keySet()) {
            Player p = players.get(i);
            if(!function){
                p.enviarMensaje("Server: "+mensaje);
            }else{
                p.enviarMensaje(mensaje);
            }
        }
    
    }
}
