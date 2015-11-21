
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Herik
 */
public class Server {
    
    ServerSocket sv;
    HashMap<String,ArrayList<Pregunta>> Preguntas;
    HashMap<Integer,Player> players;
    int currentPlayer;
    int slot=1;
    boolean juegoactivo = false;
    
    public Server(){
        int port = 12345; 
        load();
        currentPlayer=1;
        juegoactivo=true;
        try{
            System.out.println("port:12345\nIP: localHost");
            sv = new ServerSocket(port,4);
        }catch(IOException ioex) {
            ioex.getMessage();
            System.err.println("Could not listen on port: "+port+".");
        }
        players = new HashMap<Integer,Player>();
        String[] list = new String[8];
        for(Integer i = 2;i<10;i++){
            list[i-2] = i.toString();
        }
        int selec = Integer.parseInt((String)JOptionPane.showInputDialog(null, "Cuantos jugadores\nvan a jugar", "Jugadores", JOptionPane.INFORMATION_MESSAGE, null, list,list[0]));
        System.out.println("Esperando jugadores...");
        while(players.size()<selec){
            try{
                Player px = new Player( sv.accept(), slot , players, Preguntas);
                px.start();
                players.put(slot,px);
                System.out.println("conectado a: "+px.playernom+px.connection.getInetAddress()+"("+px.playerID+")");
                slot++;
            }catch(IOException ioex){
                ioex.printStackTrace();
            }
        }
        
        Player currentpx = players.get(currentPlayer);
        currentpx.suspended=false;
        currentpx.enviarTodos("Es el turno de "+currentpx.playernom,false);
        
        currentpx.enviarMensaje("Turno:-"+currentpx.playernom);
        System.out.println("next player: 1");
    }
    
    public void load(){
        Preguntas = new HashMap<String,ArrayList<Pregunta>>();
        Preguntas.put("Historia",new ArrayList<Pregunta>());
        Preguntas.put("Arte",new ArrayList<Pregunta>());
        Preguntas.put("Ciencia",new ArrayList<Pregunta>());
        Preguntas.put("Deporte",new ArrayList<Pregunta>());
        Preguntas.put("Gastronomia",new ArrayList<Pregunta>());
        Preguntas.put("Informatica",new ArrayList<Pregunta>());
        
        File aFile=new File("preguntas.txt");
        String temp="";
        String key;
        try{
            FileReader fin=new FileReader(aFile);
            BufferedReader myread=new BufferedReader(fin);
            temp=myread.readLine();
            
            while(temp!=null){                             
                String[] info = temp.split(";");
                key = info[0];
                Pregunta p = new Pregunta(info);
                ArrayList<Pregunta> ap = Preguntas.get(key);
                ap.add(p);
                temp = myread.readLine();
            }
            myread.close();
            fin.close();
        }catch(Exception ex){
            ex.getMessage();
            ex.printStackTrace();
        }
    }
    public static void main(String args[]) {
        Server s = new Server();
    }
}
