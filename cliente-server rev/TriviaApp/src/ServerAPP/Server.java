package ServerAPP;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 * @author Herik
 */
public class Server {
    
    ServerSocket sv;
    ArrayList<Pregunta> Preguntas;
    static ArrayList<Carta> Cartas;
    static int[] cartasI = {0,0,0,0};
    HashMap<Integer,Player> players;
    int currentPlayer;
    int slot=1;
    boolean juegoactivo = false;
    static Semaphore sph = new Semaphore(1,true);
        
    
    public Server(){
        int port = 12345; 
        //load();
        currentPlayer=1;
        juegoactivo=true;
        try{
            System.out.println("port:12345\nIP: localHost");
            sv = new ServerSocket(port,5);
        }catch(IOException ioex) {
            ioex.getMessage();
            System.err.println("Could not listen on port: "+port+".");
        }
        players = new HashMap<Integer,Player>();
        String[] list = new String[5];

        for(Integer i = 2;i<7;i++){
            list[i-2] = i.toString();
        }
        int selec = Integer.parseInt((String)JOptionPane.showInputDialog(null, "Cuantos jugadores\nvan a jugar", "Jugadores", JOptionPane.INFORMATION_MESSAGE, null, list,list[0]));
        System.out.println("Esperando jugadores...");
        while(players.size()<selec){
            try{
                Player px = new Player( sv.accept(), slot , players, Preguntas, Cartas);
                px.start();
                players.put(slot,px);
                System.out.println("conectado a: player "+slot+px.connection.getInetAddress()+"("+px.playerID+")");
                px.enviarMensaje("ID-"+slot);
                slot++;
            }catch(IOException ioex){
                ioex.printStackTrace();
            }
        }
        //posible ciclo
            Player currentpx = players.get(currentPlayer);
            currentpx.judge=false;
            //ver como manejar al juez
            currentpx.enviarTodos("Es el turno de "+currentpx.playernom,false);
            currentpx.enviarMensaje("Turno:-"+currentpx.playernom);
            //broadcast de las cartas
            //hacer mas aleatoria la seleccion
            currentpx.enviarTodos("Pregunta:-p"+String.format("%02d", 1)+".jpg",true);
            for(int i=0;i<4;i++){
                currentpx.enviarTodos("Cartas:-a"+String.format("%02d", i)+".jpg",true);
            }
            System.out.println("next player: 1");
        //posible ciclo
    }
    
    public void load(){
        // no utilisa load aun, se utilizara despues con mas cartas y para el llenar las cartas en el semaforo  
        Preguntas = new ArrayList<Pregunta>();
        Pregunta p;
        for(int i=0;i<1;i++){
            p = new Pregunta(i,"p"+String.format("%02d", i)+".jpg");
        }
        Cartas = new ArrayList<Carta>();
        Carta c;
        for(int i=0;i<players.size();i++){
            c = new Carta(i,"a"+String.format("%02d", i)+".jpg");
        }
    }
    public static void main(String args[]) {
        Server s = new Server();
    }
}
