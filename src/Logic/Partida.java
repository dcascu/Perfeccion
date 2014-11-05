//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Logic;

public class Partida {
    
    //Atributos
    private int cantidadFichas;
    private boolean finalizada;
    
    private Estado jugadorB;
    private Estado jugadorN;
    
    private Tablero tablero;
    private Panel panel;

    //Constructor
    public Partida(int tamanioTablero, int tamanioPanel, Estado jugB, Estado jugN, int cantidadFichas){
        panel = new Panel(tamanioPanel);
        tablero = new Tablero(tamanioTablero);
        jugadorB = jugB;
        jugadorN = jugN;
        jugadorB.getJugador().setCantFichas(cantidadFichas);
        jugadorN.getJugador().setCantFichas(cantidadFichas);
        this.cantidadFichas = cantidadFichas;
        this.finalizada = false;
    }
    
    public Partida(){
        this.cantidadFichas=0;
        this.finalizada=false;
        this.jugadorB=null;
        this.jugadorN=null;
        this.tablero=null;
        this.panel=null;
    }
    
    
    public int getCantidadFichas() {
        return cantidadFichas;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Estado getJugadorB() {
        return jugadorB;
    }

    public Estado getJugadorN() {
        return jugadorN;
    }

    public Tablero getTablero() {
        return tablero;
    }
    
    public boolean esPosicionValida(int fil, int col){
        return tablero.esPosicionValida(fil, col);
    }
    
    public void colocarFicha(int fil, int col, char ficha){
        tablero.colocarFicha(fil, col, ficha);
    }
    
    
    //devuelve espacio en blanco si partida NO finalizada
    // 'B' si ganan las blancas
    // 'N' si ganan las negras
    // 'E' si hay empate
    public char verificarFinPartida(){
        
        int fichasJugadorB = jugadorB.getJugador().getCantFichas();
        int fichasJugadorN = jugadorN.getJugador().getCantFichas();

        char resultado = '-'; //Guion significa que partida NO esta terminada
        
        if(fichasJugadorB == 0 && fichasJugadorN == 0){
            
            int cantBlancas = 0;
            int cantNegras = 0;

            //Contamos las fichas del tablero para cada jugador
            for (int i = 0; i < tablero.getLargo(); i++) {
                for (int j = 0; j < tablero.getLargo(); j++) {
                    if (tablero.getContenidoEnTablero(i, j) == 'B') {
                        cantBlancas = cantBlancas + 1;
                    } else {
                        if (tablero.getContenidoEnTablero(i, j) == 'N') {
                            cantNegras = cantNegras + 1;
                        }
                    }
                }
            }
            
            //sumamos las fichas del panel para cada jugador (si el panel esta colocado)
            if (tablero.getPanel() != null) {
                for (int i = 0; i < panel.getLargo(); i++) {
                    for (int j = 0; j < panel.getLargo(); j++) {
                        if (panel.getContenidoEnPanel(i, j) == 'B') {
                            cantBlancas = cantBlancas + 1;
                        } else {
                            if (panel.getContenidoEnPanel(i, j) == 'N') {
                                cantNegras = cantNegras + 1;
                            }    
                        }
                    }
                }
            }
            
            //Si blancas > negras
            if (cantBlancas > cantNegras) {
                jugadorB.setEstado("ganado");
                jugadorN.setEstado("perdido");
                resultado = 'B'; //ganan blancas
            } else {
                //si negras > blancas
                if (cantBlancas < cantNegras) {
                    jugadorN.setEstado("ganado");
                    jugadorB.setEstado("perdido");
                    resultado = 'N'; //ganan negras
                }
                else{
                    //sino hay empate
                    resultado = 'E';
                }
            }
            finalizada = true;
        }
        return resultado;        
    }
    
    public boolean colocarPanel(int fil, int col){
        return tablero.setPanel(panel, fil, col);
    }
    
    public boolean moverPanel(String dir, int cantMovimientos){
        return tablero.moverPanel(dir, cantMovimientos);
    }    
    
    public void abandonar(Estado j){
        finalizada = true;
        if(j.getJugador().getAlias().equalsIgnoreCase(jugadorB.getJugador().getAlias())){
            jugadorB.setEstado("perdido");
            jugadorN.setEstado("ganado");
        }
        else{
            jugadorN.setEstado("perdido");
            jugadorB.setEstado("ganado");            
        }
            
    }
    
    public char[][] matrizSimetrias(int fil, int col){
        return tablero.matrizSimetrias(fil, col);
    }
    
}
