//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Logic;

import java.util.Comparator;


public class JugadorAliasComparador implements Comparator<Jugador>{
    
    @Override 
    public int compare (Jugador j1, Jugador j2){
        return j1.getAlias().compareTo(j2.getAlias());
    }
    
}