//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Presentation;


public class TipoJugada {
    private String comando;
    private int fila;
    private int columna;
    
    private int cantFil;
    private int cantCol;
    
    public TipoJugada(String comando, int fil, int col, int cantF, int cantC){
        this.comando = comando;
        this.fila = fil;
        this.columna = col;
        this.cantFil = cantF;
        this.cantCol = cantC;
    }

    public String getComando() {
        return comando;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
    public int getCantFila(){
        return cantFil;
    }

    public int getCantColumna(){
        return cantCol;
    }
    
}
