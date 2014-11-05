//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

//
enum estados {

    ganado, perdido, empatado

}

public class Sistema {

    //Atributos
    private ArrayList<Jugador> jugadores;
    private static Partida partidaActual;

    //Constructor
    public Sistema() {

        jugadores = new ArrayList<Jugador>();
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    //Metodos de jugador
    public boolean ingresarJugador(String nombre, String alias, int edad) {

        boolean encontre = false;
        Iterator it = jugadores.iterator();
        while (it.hasNext() && !encontre) {
            Jugador j = (Jugador) it.next();
            if (j.getAlias().equals(alias)) {
                encontre = true;
            }
        }

        if (!encontre) {
            Jugador j = new Jugador(nombre, alias, edad);
            jugadores.add(j);
        }
        return !encontre;
    }

    public ArrayList<Jugador> listaOrdenadaJugadores() {

        //clon lista jugadores
        ArrayList<Jugador> listaOrdenada = (ArrayList) jugadores.clone();

        Collections.sort(listaOrdenada, new JugadorAliasComparador());

        return listaOrdenada;

    }

    public Jugador getUnJugador(String alias) {
        Iterator it = jugadores.iterator();
        while (it.hasNext()) {
            Jugador j = (Jugador) it.next();
            if (j.getAlias().equals(alias)) {
                return j;
            }
        }
        return null;
    }

    //Metodos de partida
    public Partida getPartidaActual() {
        return partidaActual;
    }

    //Metodos de Jugar
    public void iniciarPartida(Jugador jB, Jugador jN, int tamTablero, int tamPanel, int cantFichas) {
        Estado eB = new Estado();
        eB.setEstado("");
        eB.setJugador(jB);
        jB.agregarPartida(eB);

        Estado eN = new Estado();
        eN.setEstado("");
        eN.setJugador(jN);
        jN.agregarPartida(eN);

        partidaActual = new Partida(tamTablero, tamPanel, eB, eN, cantFichas);
    }

    public void abandonarPartida(Estado j) {
        partidaActual.abandonar(j);
    }

    //turno = "B" o "N"
    // fil y col son posiciones validas del tablero
    //Retorna la matriz de simetrias
    public char[][] colocarFicha(char turno, int fil, int col) {

        Tablero tablero = partidaActual.getTablero();
        int tam = tablero.getLargo();
        char[][] simetrias = new char[tam][tam];

        tablero.colocarFicha(fil, col, turno);

        //resto uno a las fichas del jugador correspondiente
        int fichasDelJugador;
        if (turno == 'B') {
            fichasDelJugador = partidaActual.getJugadorB().getJugador().getCantFichas();
            partidaActual.getJugadorB().getJugador().setCantFichas(fichasDelJugador - 1);
        } else {
            fichasDelJugador = partidaActual.getJugadorN().getJugador().getCantFichas();
            partidaActual.getJugadorN().getJugador().setCantFichas(fichasDelJugador - 1);

        }

        //verificar y armar matriz de simetrias
        simetrias = partidaActual.matrizSimetrias(fil, col);

        return simetrias;
    }

    public void cambiarFichasTablero(char turno, char[][] simetrias) {
        partidaActual.getTablero().cambiarFichas(turno, simetrias);
    }

    public boolean validarPosicion(int fil, int col) {
        return partidaActual.esPosicionValida(fil, col);
    }

    //verifica el fin de la partida y si es true cuenta las fichas de cada jugador y setea el estado correspondiente
    public char verificarFinPartida() {
        return partidaActual.verificarFinPartida();
    }

    public boolean estaPanel() {
        return partidaActual.getTablero().getPanel() != null;
    }

    public boolean colocarPanel(int fil, int col) {
        return partidaActual.colocarPanel(fil, col);
    }

    public boolean moverPanel(String dir, int cantMovimientos) {
        return partidaActual.moverPanel(dir, cantMovimientos);
    }

}
