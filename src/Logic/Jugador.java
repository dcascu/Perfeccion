//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Jugador implements Comparable<Jugador> {

    private String nombre;
    private String alias;
    private int edad;
    private ArrayList<Estado> partidas;
    private int cantFichas;

    public Jugador(String nom, String alias, int edad) {
        this.nombre = nom;
        this.alias = alias;
        this.edad = edad;
        this.partidas = new ArrayList<Estado>();
        this.cantFichas =0;
    }
    
    public Jugador(){
        this.nombre = "";
        this.alias = "";
        this.edad = 0;
        this.partidas = null;    
        this.cantFichas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlias() {
        return alias;
    }

    public int getEdad() {
        return edad;
    }

    public void agregarPartida(Estado e){
        this.partidas.add(e);
    }

    public int getCantFichas() {
        return cantFichas;
    }

    public void setCantFichas(int cantFichas) {
        this.cantFichas = cantFichas;
    }
       

    public int cantidadPartidasJugadas() {
        return partidas.size();

    }

    public int cantPartidasGanadas(){
        
        int cont = 0;
        
        Iterator it = partidas.iterator();
        while(it.hasNext()){
            Estado e = (Estado)it.next();
            if(e.getEstado().equals("ganado")){
                cont = cont+1;
            }   
        }
        return cont;
             
    }
    
    
    @Override
    public int compareTo(Jugador unJ) {
        return this.getAlias().compareTo(unJ.getAlias());
    }
    
     @Override
    public String toString() {
        return "Nombre: " + this.getNombre() + "\nEdad:" + this.getEdad() + "\nAlias: " + this.getAlias() ;
    }

}


