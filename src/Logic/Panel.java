//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Logic;

public class Panel {
    
    private int tamanio;
    private char[][] panel;
    private int fila;
    private int columna;
    
    public Panel(int tamanio){
        this.tamanio = tamanio;
        
        this.panel = new char[tamanio][tamanio];
        
        //definimos el tamaño e inicializamos panel con espacios en blanco
        this.panel = new char[tamanio][tamanio];
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                panel[i][j] = ' ';
            }
        }
    }
    
    public Panel(){
        this.tamanio = 0;
        this.panel = null;
        this.fila = 0;
        this.columna = 0;
        
    }

    public int getFila() {
        return fila;
    }

    public char[][] getMatrizPanel() {
        return panel;
    }

    
    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getTamanio() {
        return tamanio;
    }
    
    public void agregarFicha(char ficha, int fil, int col){
        panel[fil][col] = ficha;
    }
    
    public int getLargo() {
        return panel.length;
    }
    
    public char getContenidoEnPanel(int fila, int col){
        return panel[fila][col];
    } 
}
