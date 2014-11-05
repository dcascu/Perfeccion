//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Logic;

public class Tablero {

    //Atributos
    private int tamanio;
    private char[][] tablero;
    private Panel panel;
    private boolean imprimirAsteriscos = false;
    private int filaCero;
    private int colCero;
    
    private int contador;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
    



    //Constructor
    public Tablero(int tamanio) {

        this.tamanio = tamanio;
        this.panel = null;

        tablero = new char[tamanio][tamanio];

        //definimos el tamaño e inicializamos tablero con espacios en blanco
        this.tablero = new char[tamanio][tamanio];
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                tablero[i][j] = ' ';
            }
        }

    }
    
    public Tablero(){
        this.tamanio=0;
        this.tablero=null;
        this.panel=null;
        this.imprimirAsteriscos=false;
        this.filaCero=0;
        this.colCero=0;
    }

    public char[][] getMatrizTablero() {
        return tablero;
    }

    public void setTablero(char[][] tablero) {
        this.tablero = tablero;
    }

    public char getContenidoEnTablero(int fila, int col) {
        return tablero[fila][col];
    }

    public boolean setPanel(Panel panel, int fila, int col) {

        if ((fila <= (tablero.length - panel.getLargo())) && (col <= (tablero.length - panel.getLargo()))) {
            this.panel = panel; //aca asocio mi tablero con el panel que tenia la partida
            this.panel.setFila(fila);
            this.panel.setColumna(col);
            return true;
        }
        return false;
    }

    //movimientos del panel
    public boolean moverPanel(String dir, int cantMovimientos) {

        switch (dir) {

            case "izq":
                if (cantMovimientos <= panel.getColumna()) {
                    panel.setColumna(panel.getColumna() - cantMovimientos);
                    return true;
                }
                return false;

            case "der":
                if (cantMovimientos <= tablero.length - panel.getColumna() - panel.getLargo()) {
                    panel.setColumna(panel.getColumna() + cantMovimientos);
                    return true;
                }
                return false;

            case "arr":
                if (cantMovimientos <= panel.getFila()) {
                    panel.setFila(panel.getFila() - cantMovimientos);
                    return true;
                }
                return false;

            case "aba":
                if (cantMovimientos <= tablero.length - panel.getFila() - panel.getLargo()) {
                    panel.setFila(panel.getFila() + cantMovimientos);
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public Panel getPanel() {
        return panel;
    }

    public int getLargo() {
        return tablero.length;
    }

    //verificamos si en la posicion dada hay panel o no
    //si no hay panel verificamos si la posicion esta vacía
    //si está vacía colocamos la ficha en el tablero
    //si está el panel, verificamos que la posicion correspondiente del panel esté vacía
    //si está vacía colocamos la ficha en el panel
    //si la posiciónn en el tablero/panel no está vacía, no esPosicionValida
    public boolean esPosicionValida(int fil, int col) {

        if (fil >= 0 && fil <= tamanio - 1 && col >= 0 && col <= tamanio - 1) {

            //esta el panel
            if (panel != null) {
                //verificar si la posicion corresponde al panel o no
                if (fil >= panel.getFila() && fil <= panel.getFila() + panel.getTamanio() - 1
                        && col >= panel.getColumna() && col <= panel.getColumna() + panel.getTamanio() - 1) {
                    if (panel.getContenidoEnPanel((fil - panel.getFila()), (col - panel.getColumna())) == ' ') {
                        return true;
                    }
                } else {
                    if (tablero[fil][col] == ' ') {
                        return true;
                    }
                }
            } else {
                if (tablero[fil][col] == ' ') {
                    return true;
                }
            }
        }
        return false;
    }



    //SIMETRIAAA
    public void cambiarFichas(char ficha, char[][] simetrias) {

        //i = fila, j = columna
        for (int i = 0; i <= tamanio - 1; i++) {
            for (int j = 0; j <= tamanio - 1; j++) {
                if (simetrias[i][j] == '*') {

                    //esta el panel
                    if (panel != null) {
                        //verificar si la posicion corresponde al panel o no
                        if (i >= panel.getFila() && i <= panel.getFila() + panel.getTamanio() - 1
                                && j >= panel.getColumna() && j <= panel.getColumna() + panel.getTamanio() - 1) {
                            panel.getMatrizPanel()[(i - panel.getFila())][(j - panel.getColumna())] = ficha;
                        } else {
                            tablero[i][j] = ficha;
                        }
                    } else {
                        tablero[i][j] = ficha;
                    }
                }
            }
        }
    }

    public char[][] matrizSimetrias(int fil, int col) {

        char[][] resultado = new char[tamanio][tamanio];
        for (int i = 0; i < resultado.length; i++) {
            for (int j = 0; j < resultado[i].length; j++) {
                resultado[i][j] = ' ';
                contador = 0;
            }
        }
        //4 --> 3 --> 2
        for (int tamSimetria = 4; tamSimetria >= 2; tamSimetria--) {
            for (int i = fil; i < tamanio; i++) {
                for (int j = col; j < tamanio; j++) {
                    //hacer metodo calcular fila y columna cero
                    if ((i < (tamSimetria + fil)) && (j < (tamSimetria + col))) {
                        calcularFilaColumna(i, j, tamSimetria);
                    } else {
                        filaCero = -1;
                        colCero = -1;
                    }
                    if (filaCero != -1) { //aca la matriz de simetria a analizar no se sale del tablero
                        //la filaCero corresponde a la fila cero de la matriz a analizar
                        //la colCero corresponde a la columna cero de la matriz a analizar
                        char[][] matrizParaAnalizar = matrizAnalizada(filaCero, colCero, tamSimetria);
                        if (esSimetrica(matrizParaAnalizar)) {
                            contador++;
                            for (int k = filaCero; k < (tamSimetria + filaCero); k++) {
                                for (int r = colCero; r < (tamSimetria + colCero); r++) {
                                    resultado[k][r] = '*';
                                    imprimirAsteriscos = true;
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        
        return resultado;
    }

    //Devuelve si una matriz cualquiera es simetrica
    private boolean esSimetrica(char[][] matriz) {
        int tamSimetria = matriz.length;
        boolean simetrica = true;
        int x = 0;
        int y = 0;
        boolean medioVacio = false;
        boolean mismoColor = true;

        switch (tamSimetria) {
            case 4:
                //verificamos si la matriz es de un mismo color
                for(int i=0; ((i<matriz.length)&&(mismoColor)); i++){
                   for(int j=0; (j<matriz[i].length&&(mismoColor)); j++){
                       if (matriz[i][j]!=matriz[0][0]){
                           mismoColor = false;
                       }
                   } 
                }
                //verificamos si es horizontal
                while (simetrica && x <= 3) { //verificamos si es horizontal
                    if ((mismoColor)||(matriz[0][x] == ' ') || (matriz[3][x] == ' ') || (matriz[1][x] == ' ') || (matriz[2][x] == ' ')
                            || ((matriz[0][x] != matriz[3][x]) || (matriz[1][x] != matriz[2][x]))) {
                        simetrica = false;
                    }
                    x++;
                }

                //verificamos si es vertical solo si horizontal no es simetrica
                if (simetrica == false) {
                    simetrica = true;
                    while (simetrica && y <= 3) { //verificamos si es horizontal
                        if ((mismoColor)||(matriz[0][y] == ' ') || (matriz[3][y] == ' ') || (matriz[1][y] == ' ') || (matriz[2][y] == ' ') 
                                || ((matriz[y][0] != matriz[y][3]) || (matriz[y][1] != matriz[y][2]))) {
                            simetrica = false;
                        }
                        y++;
                    }
                }
                if (mismoColor){
                    imprimirAsteriscos = true;
                }
                break;

            case 3:
                //verificamos si la matriz es de un mismo color
                for(int i=0; ((i<matriz.length)&&(mismoColor)); i++){
                   for(int j=0; (j<matriz[i].length&&(mismoColor)); j++){
                       if (matriz[i][j]!=matriz[0][0]){
                           mismoColor = false;
                       }
                   } 
                }
                //verifico que la fila o columna del medio de la matriz no sea vacia
                for (int i = 0; i < matriz.length; i++) {
                    if ((matriz[i][1]) == ' ') {
                        medioVacio = true;
                    }
                    if ((matriz[1][i]) == ' ') {
                        medioVacio = true;
                    }
                }
                if ((!(medioVacio))&&(!(mismoColor))) {
                    //verificamos si es horizontal
                    while (simetrica && x <= 2) { //verificamos si es horizontal
                        if ((matriz[0][x] == ' ') || (matriz[0][x] != matriz[2][x])) {
                            simetrica = false;
                        }
                        x++;
                    }

                    //verificamos si es vertical solo si horizontal no es simetrica
                    if (simetrica == false) {
                        simetrica = true;
                        while (simetrica && y <= 2) { //verificamos si es horizontal
                            if ((matriz[y][0] == ' ') || (matriz[y][0] != matriz[y][2])) {
                                simetrica = false;
                            }
                            y++;
                        }
                    }
                } else {
                    simetrica = false;
                }
                if (mismoColor){
                    imprimirAsteriscos = true;
                }
                break;

            case 2:
                //verificamos si es horizontal
                simetrica = false;
                if (((matriz[0][0] != ' ') && (matriz[0][1] != ' ')) && (matriz[0][0] == matriz[1][0]) && (matriz[0][1] == matriz[1][1])&&(matriz[0][0]!=matriz[1][1])) {
                    simetrica = true;
                }

                //verificamos si es vertical solo si horizontal no es simetrica
                if (simetrica == false) {
                    if (((matriz[0][0] != ' ') && (matriz[1][0] != ' ')) && (matriz[0][0] == matriz[0][1]) && (matriz[1][0] == matriz[1][1])&&(matriz[0][0]!=matriz[1][1])) {
                        simetrica = true;
                    }
                }
                if ((matriz[0][0]==matriz[1][1]) && (matriz[0][1]==matriz[1][0]) && (matriz[0][0] != ' ')&& (matriz[0][1] != ' ')){
                    imprimirAsteriscos = true;
                }
                break;
        }
        return simetrica;
    }

    public boolean isImprimirAsteriscos() {
        return imprimirAsteriscos;
    }

    public void setImprimirAsteriscos(boolean imprimirAsteriscos) {
        this.imprimirAsteriscos = imprimirAsteriscos;
    }

    //me pasan la posicion cero de la matriz a analizar y debo construirla en base al tamanio de simetria
    //teniendo en cuenta si esta o no el panel
    private char[][] matrizAnalizada(int fil, int col, int tamSimetria) {

        char[][] resultado = new char[tamSimetria][tamSimetria];
        int Ftablero = fil;
        int Ctablero = col;
        int maximoF;
        int maximoC;
        boolean agregoDelT = false;
        boolean agregoDelP = false;
        boolean cambioDeFilaConP = false;

        maximoC = tamSimetria + col;
        maximoF = tamSimetria + fil;

        for (int i = fil; i < maximoF; i++) {
            for (int j = col; j < maximoC; j++) {
                //esta el panel
                if (panel != null) {
                    //verificar si la posicion corresponde al panel o no
                    if (((i >= panel.getFila()) && (i < (panel.getFila() + panel.getTamanio()))
                            && ((j >= panel.getColumna()) && (j < panel.getColumna() + panel.getTamanio())))) {
                        resultado[i - fil][j - col] = panel.getContenidoEnPanel(i-panel.getFila(),j-panel.getColumna());
                        
                    } else {
                        resultado[i - fil][j - col] = tablero[Ftablero][Ctablero];
                    }
                } else {
                    resultado[i - fil][j - col] = tablero[Ftablero][Ctablero];
                }
                Ctablero = Ctablero + 1;
            }
            Ftablero = Ftablero + 1;

            Ctablero = col;
        }

        return resultado;
    }

    private void calcularFilaColumna(int fil, int col, int tamSimetria) {
        if (((fil - (tamSimetria - 1)) < 0) || (col - (tamSimetria - 1) < 0)) {
            filaCero = -1;
            colCero = -1;
        } else {
            filaCero = (fil - (tamSimetria - 1));
            colCero = (col - (tamSimetria - 1));
        }
    }
    
        //Pre: fil, col son validos
    public void colocarFicha(int fil, int col, char ficha) {

        if (fil >= 0 && fil <= tamanio - 1 && col >= 0 && col <= tamanio - 1) {

            //esta el panel
            if (panel != null) {
                //verificar si la posicion corresponde al panel o no
                if (fil >= panel.getFila() && fil <= panel.getFila() + panel.getTamanio() - 1
                        && col >= panel.getColumna() && col <= panel.getColumna() + panel.getTamanio() - 1) {
                    panel.getMatrizPanel()[(fil - panel.getFila())][(col - panel.getColumna())] = ficha;
                } else {
                    tablero[fil][col] = ficha;
                }
            } else {
                tablero[fil][col] = ficha;
            }
        }
    }
    
    
        public int cantidadFichasFiloCol(int filoCol, boolean esFila) {
        //recibe en filoCol el numero de la fila o la columna, y en esFila true si el numero es de una fila o false sino.
        int cantidadFichas = 0;
		if (esFila){
			//i = fila, j = columna
			int i = filoCol;
			for (int j = 0; j <= tamanio - 1; j++) {
				//esta el panel
				if (panel != null) {
					//verificar si la posicion corresponde al panel o no
					if (i >= panel.getFila() && i <= panel.getFila() + panel.getTamanio() - 1
							&& j >= panel.getColumna() && j <= panel.getColumna() + panel.getTamanio() - 1) {
						if (panel.getMatrizPanel()[(i - panel.getFila())][(j - panel.getColumna())] !=  ' '){
						    cantidadFichas=cantidadFichas+1;
						}
					} else {
						if (tablero[i][j] !=  ' '){
						    cantidadFichas=cantidadFichas+1;
						}
					}
				} else {
					if (tablero[i][j] !=  ' '){
						    cantidadFichas=cantidadFichas+1;
					}
				}				
			}
			
		}
		else{
		    //i = fila, j = columna
			int j = filoCol;
			for (int i = 0; i <= tamanio - 1; i++) {
				//esta el panel
				if (panel != null) {
					//verificar si la posicion corresponde al panel o no
					if (i >= panel.getFila() && i <= panel.getFila() + panel.getTamanio() - 1
							&& j >= panel.getColumna() && j <= panel.getColumna() + panel.getTamanio() - 1) {
						if (panel.getMatrizPanel()[(i - panel.getFila())][(j - panel.getColumna())] !=  ' '){
						    cantidadFichas=cantidadFichas+1;
						}
					} else {
						if (tablero[i][j] !=  ' '){
						    cantidadFichas=cantidadFichas+1;
						}
					}
				} else {
					if (tablero[i][j] !=  ' '){
						    cantidadFichas=cantidadFichas+1;
					}
				}
			}
		}
		
		return cantidadFichas;
    }
}
