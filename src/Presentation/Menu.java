//Daniela Dodel (166689) - Nicolás Dotti (191333)

package Presentation;

import Logic.*;
import java.util.*;

public class Menu {

    private static Scanner teclado = null;

    static Partida partidaAct;
    //static boolean panelEsta = false;
    static boolean turnoBlanco = true;
    //agregada nueva variable para saber si abandono o no
    //static boolean abandono = false;
    static char partidaTerminada = '-'; //guion significa partida NO terminada
    static char[][] simetrias;
    static int fichasDelJugador;

    public static void main(String[] args) {

        teclado = new Scanner(System.in);
        Sistema sistema = new Sistema();

        //menu principal - punto de entrada
        String opcion;
        String ingreso = " ";
        String salir = "N";

        while (salir.equals("N")) {

            menuOpciones();
            opcion = teclado.nextLine();
            switch (opcion.toUpperCase()) {

                case "A":
                    //Ingresar jugador
                    ingresarJugadorMenu(sistema);
                    break;

                case "B":
                    //Jugar a perfeccion
                    if (sistema.getJugadores().size() >= 2) {
                        
                        Jugador miJugador1 = seleccionarJugador(null, sistema);
                        Jugador miJugador2 = seleccionarJugador(miJugador1, sistema);
                        boolean panelColocado;

                        //teclado.nextLine();
                        int tamTablero = validarTamanioTablero();
                        int tamPanel = validarTamanioPanel(tamTablero);
                        int cantFichas = cantidadFichasSeleccionadas(tamTablero, tamPanel);
                        teclado.nextLine();
                        sistema.iniciarPartida(miJugador1, miJugador2, tamTablero, tamPanel, cantFichas);
                        partidaAct = sistema.getPartidaActual();
                        System.out.println("");
                        while (partidaTerminada == '-') {
                            System.out.println("Tablero de juego actual:");
                            imprimirTablero(sistema);
                            movimientosPosibles();
                            TipoJugada jugada = pedirJugada(sistema);

                            switch (jugada.getComando()) {
                                case "abandonar":
                                    if (turnoBlanco) {
                                        sistema.abandonarPartida(partidaAct.getJugadorB());
                                        partidaTerminada = 'N';
                                    } else {
                                        sistema.abandonarPartida(partidaAct.getJugadorN());
                                        partidaTerminada = 'B';
                                    }
                                    break;

                                case "ficha":

                                    //validar posicion y que tenga fichas
                                    if (turnoBlanco) {
                                        fichasDelJugador = sistema.getPartidaActual().getJugadorB().getJugador().getCantFichas();
                                    } else {
                                        fichasDelJugador = sistema.getPartidaActual().getJugadorN().getJugador().getCantFichas();
                                    }
                                    if ((sistema.validarPosicion(jugada.getFila(), jugada.getColumna())) && (fichasDelJugador != 0)) {

                                        char turno;
                                        if (turnoBlanco) {
                                            turno = 'B';
                                        } else {
                                            turno = 'N';
                                        }
                                        
                                        //aca llamar a sistema para colocar ficha --> devuelve matriz simetrias
                                        simetrias = sistema.colocarFicha(turno, jugada.getFila(), jugada.getColumna());
                                        
                                        //imprimir matriz de simetrias obtenida
                                        if (sistema.getPartidaActual().getTablero().isImprimirAsteriscos()) {
                                            System.out.println("");
                                            System.out.println("Tablero de cambios: ");
                                            imprimirTablero(sistema);
                                            sistema.getPartidaActual().getTablero().setImprimirAsteriscos(false);
                                        }

                                        //aca llamar a sistema para cambiar fichas del tablero
                                        sistema.cambiarFichasTablero(turno, simetrias);

                                        //aca llamar a sistema para verificar fin de la partida
                                        partidaTerminada = sistema.verificarFinPartida();
                                        if (partidaTerminada == '-') {//NO termino la partida
                                            //cambiar el turno
                                            turnoBlanco = !turnoBlanco;
                                        }
                                        System.out.println("");
                                        System.out.println("Tablero resultante:");
                                        imprimirTablero(sistema);
                                        
//                                        //muestro la cantidad de fichas en la fila y la columna donde se coloca la ficha
//                                        System.out.println("");
//                                        //pongo true porque quiero la cantidad de fichas en la fila y le paso la fila en donde se coloco la ficha.
//                                        System.out.println("Cantidad fichas en fila:"+sistema.getPartidaActual().getTablero().cantidadFichasFiloCol(1,true));
//                                        System.out.println("");
//                                        //pongo false porque quiero la cantidad de fichas en la columna y le paso la columna en donde se coloco la ficha.
//                                        System.out.println("Cantidad fichas en columna:"+sistema.getPartidaActual().getTablero().cantidadFichasFiloCol(3,false));
////ACA TERMINA------->>>>>>>>            System.out.println("");
                                        
                                        System.out.println("Cantidad de simetrias: "+ sistema.getPartidaActual().getTablero().getContador());
                                        
                                        
                                    } else {
                                        if ((sistema.validarPosicion(jugada.getFila(), jugada.getColumna()))&&(fichasDelJugador == 0)) {
                                            System.out.println("No se puede colocar la ficha porque el jugador no tiene fichas, solo puede colocar o mover el panel");
                                        } else {
                                            System.out.println("Coordenadas no válidas, o ya hay una ficha en esas coordenadas.");
                                        }
                                        System.out.println("");
                                    }

                                    break;

                                case "panel":

                                    //verificamos que el panel no este 
                                    panelColocado = sistema.estaPanel();

                                    if (!panelColocado) { //si el panel NO esta
                                        //colocamos el panel
                                        panelColocado = sistema.colocarPanel(jugada.getFila(), jugada.getColumna());
                                        if (!panelColocado) //si la posicion era invalida
                                        {
                                            System.out.println("Coordenadas no válidas.");
                                        } else {
                                            turnoBlanco = !turnoBlanco; //cambio el turno
                                            System.out.println("Panel colocado con éxito.");
                                        }
                                    } else {
                                        System.out.println("El panel ya está colocado.");
                                    }
                                    System.out.println("");
                                    System.out.println("Tablero resultante:");
                                    imprimirTablero(sistema);
                                    break;

                                case "not valid":
                                    System.out.println("Jugada no válida.");
                                    System.out.println("");
                                    break;

                                default:

                                    //verificamos que el panel no este 
                                    panelColocado = sistema.estaPanel();

                                    if (panelColocado) { //si el panel esta movemos
                                        boolean panelMovido = false;
                                        if (jugada.getCantFila() != 0) {
                                            panelMovido = sistema.moverPanel(jugada.getComando(), jugada.getCantFila());
                                        } else {
                                            panelMovido = sistema.moverPanel(jugada.getComando(), jugada.getCantColumna());
                                        }
                                        if (!panelMovido) //si la posicion era invalida
                                        {
                                            System.out.println("Movimiento no válido.");
                                        } else {
                                            turnoBlanco = !turnoBlanco; //cambio el turno
                                            System.out.println("Panel movido con éxito.");
                                        }
                                    } else {
                                        System.out.println("El panel NO está colocado.");
                                    }
                                    System.out.println("");
                                    System.out.println("Tablero resultante:");
                                    imprimirTablero(sistema);
                                    
                                   
                                    
                                    break;
                                    
                                    
                            }
                        }

                        if (partidaTerminada != '-') {
                            System.out.println("Partida finalizada!!");

                            switch (partidaTerminada) {
                                case 'B':
                                    System.out.println("Ganaron las blancas.");
                                    System.out.println("Alias del jugador ganador(Blanco): "+sistema.getPartidaActual().getJugadorB().getJugador().getAlias());
                                    System.out.println("Alias del jugador perdedor(Negro): "+sistema.getPartidaActual().getJugadorN().getJugador().getAlias());
                                    break;
                                case 'N':
                                    System.out.println("Ganaron las negras.");
                                    System.out.println("Alias del jugador ganador(Negro): "+sistema.getPartidaActual().getJugadorN().getJugador().getAlias());
                                    System.out.println("Alias del jugador perdedor(Blanco): "+sistema.getPartidaActual().getJugadorB().getJugador().getAlias());
                                    break;
                                case 'E':
                                    System.out.println("Empate.");
                                    System.out.println("Alias del jugador Blanco: "+sistema.getPartidaActual().getJugadorB().getJugador().getAlias());
                                    System.out.println("Alias del jugador Negro: "+sistema.getPartidaActual().getJugadorN().getJugador().getAlias());
                                    break;
                                default:
                                    break;
                            }

                        }
                    } else {
                        System.out.println("Deben haber por lo menos dos jugadores ingresados para jugar a Perfección. ");
                        System.out.println("Si desea jugar, por favor ingrese más jugadores.");
                    }
                    partidaTerminada = '-';
                    turnoBlanco = true;
                    break;

                case "C":
                    //Consulta de jugadores
                    ArrayList<Jugador> lista = sistema.listaOrdenadaJugadores();

                    if (lista.isEmpty()) {
                        System.out.println("No se han registrado Jugadores.");
                    } else {
                        Iterator it = lista.iterator();
                        while (it.hasNext()) {
                            Jugador j = (Jugador) it.next();
                            System.out.println(j.toString());
                            System.out.println();
                            System.out.println("Partidas jugadas: " + j.cantidadPartidasJugadas());
                            System.out.println("Partidas ganadas: " + j.cantPartidasGanadas());
                            System.out.println("---------------------------------");
                        }
                    }
                    break;

                case "D":
                    //Fin
                    System.out.println("Seguro que desea salir (S/N)?");
                    ingreso = teclado.nextLine().toUpperCase();
                    while (!(ingreso.equals("S") || ingreso.equals("N"))) {
                        teclado.reset();
                        System.out.println("Opción inválida, debe oprimir S para salir, o N para volver al menú principal.");
                        System.out.println("Ingrese nuevamente:");
                        ingreso = teclado.nextLine().toUpperCase();
                    }
                    if (ingreso.equals("S")) {
                        salir = "S";
                        System.out.println("Gracias por jugar!! ");
                    }
                    if (ingreso.equals("N")) {
                        salir = "N";
                    }
                    break;

                default:
                    System.out.println("Ingrese una opción valida (A, B, C, D)");
                    System.out.println("Gracias.");
            }
        }
        System.exit(0);

    }

    private static void movimientosPosibles() {
        System.out.println(" ");
        System.out.println("                ----- Movimientos posibles -----");
        System.out.println(" ");
        System.out.println("JFilaColumna --> Corresponde a poner una ficha de su color en esa posición.");
        System.out.println("PFilaColumna --> Corresponde a ingresar el panel y colocarlo desde la posición FilaColumna.");
        System.out.println("         Movimientos del panel: ");
        System.out.println("MIn --> Mover el panel hacia la izquierda n columnas.");
        System.out.println("MDn --> Mover el panel hacia la derecha n columnas.");
        System.out.println("MAn --> Mover el panel hacia arriba n filas.");
        System.out.println("MBn --> Mover el panel hacia abajo n filas.");
        System.out.println(" ");
    }

    private static void menuOpciones() {
        System.out.println("----- Menu Principal -----\n");
        System.out.println("A) Ingresar jugador.");
        System.out.println("B) Jugar a Perfección.");
        System.out.println("C) Consulta de jugadores.");
        System.out.println("D) Fin");
    }

    private static void ingresarJugadorMenu(Sistema sistema) {

        String nombre = validarString("Ingrese nombre: ", "El nombre no debe ser vacío.\n");
        String alias = validarString("Ingrese alias: ", "El alias no debe ser vacío.\n");
        int edad = validarInt("Ingrese edad: ", 1, 99, "El dato debe ser un número",
                "Debe ingresar una edad entre 1 y 99.");

        boolean exito = sistema.ingresarJugador(nombre, alias, edad);
        if (exito) {
            System.out.println("Jugador ingresado correctamente.");
        } else {
            System.out.println("Ya existe un jugador con alias = " + alias);
        }
        teclado.nextLine();
    }

    //seleccion de jugadores
    public static Jugador seleccionarJugador(Jugador elOtroJugador, Sistema sistema) {
        //asumimos que se seleccionan los jugadores con su alias ya que este es único
        //imprimir lista de jugadores
        //Si elOtroJugador == null --> imprimo todos sino Imprimo todos menos elOtroJugador
        Iterator it = sistema.getJugadores().iterator();
        System.out.println("Por favor seleccione un jugador: ");
        while (it.hasNext()) {
            Jugador j = (Jugador) it.next();
            if ((elOtroJugador != null && !(elOtroJugador.getAlias().equalsIgnoreCase(j.getAlias()))) || elOtroJugador == null) {
                System.out.println("- Alias: " + j.getAlias());
            }
        }

        Jugador retorno;
        do {
            String aliasSeleccion = validarString("Ingrese alias: ", "El alias no debe ser vacío.\n");

            retorno = sistema.getUnJugador(aliasSeleccion);
            if (retorno == null) {
                System.out.println("Alias invalido");
                it = sistema.getJugadores().iterator();
                System.out.println("Por favor seleccione un jugador: ");
                while (it.hasNext()) {
                    Jugador j = (Jugador) it.next();
                    if ((elOtroJugador != null && !(elOtroJugador.getAlias().equalsIgnoreCase(j.getAlias()))) || elOtroJugador == null) {
                        System.out.println("- Alias: " + j.getAlias());
                    }
                }
            }

            if (elOtroJugador != null && retorno != null && retorno.getAlias().equalsIgnoreCase(elOtroJugador.getAlias())) {
                System.out.println("Ese jugador ya fue seleccionado, seleccione otro: ");
                seleccionarJugador(elOtroJugador, sistema);
            }

        } while (retorno == null);
        return retorno;

    }

    //validacion tam tablero
    public static int validarTamanioTablero() {
        System.out.println("Ingrese el tamaño del tablero deseado(4-6-8): ");
        String tamaño = teclado.nextLine();

        while (!(tamaño.equals("4") || tamaño.equals("6") || tamaño.equals("8"))) {
            System.out.println("Debe ingresar un tamaño de tablero válido 4 - (4x4) ó, 6 - (6x6) ó, 8 - (8x8)");
            System.out.println("Ingrese el tamaño del tablero: ");
            tamaño = teclado.nextLine();
        }

        int tam = Integer.parseInt(tamaño);
        return tam;
    }

    //validacion tam panel
    public static int validarTamanioPanel(int tamTablero) {
        System.out.println("Ingrese el tamaño del panel deseado(2-3-4):");
        String tamanio = teclado.nextLine();

        if (tamTablero == 4) {
            while (!(tamanio.equals("2"))) {
                System.out.println("Debe ingresar un tamaño de panel válido 2-(2x2)");
                System.out.println("Ingrese el tamaño del panel: ");
                tamanio = teclado.nextLine();
            }
        }

        if (tamTablero == 6) {
            while (!(tamanio.equals("2") || (tamanio.equals("3")))) {
                System.out.println("Debe ingresar un tamaño de panel válido 2-(2x2), 3-(3x3)");
                System.out.println("Ingrese el tamaño del panel: ");
                tamanio = teclado.nextLine();
            }
        }

        if (tamTablero == 8) {
            while (!((tamanio.equals("2")) || (tamanio.equals("3")) || (tamanio.equals("4")))) {
                System.out.println("Debe ingresar un tamaño de panel válido 2-(2x2), 3-(3x3), 4-(4x4)");
                System.out.println("Ingrese el tamaño del panel: ");
                tamanio = teclado.nextLine();
            }
        }

        return Integer.parseInt(tamanio);
    }

    //validacion cantidad de fichas de jugador
    public static int cantidadMaxFichas(int tamTablero, int tamPanel) {
        int FichasTablero = tamTablero * tamTablero;
        int FichasPanel = tamPanel * tamPanel;

        int cantMax = (FichasTablero + FichasPanel) / 2;

        return cantMax;
    }

    public static int cantidadFichasSeleccionadas(int tamTablero, int tamPanel) {
        //modificado para dar error si ingresan algo que no sea un numero
        int cantFichas = 0;
        int tamMaximoFichas = cantidadMaxFichas(tamTablero, tamPanel);
        boolean ok = false;
        while (!ok) {
            try {
                System.out.println("Ingrese cantidad de fichas:");
                cantFichas = teclado.nextInt();
                while ((cantFichas > tamMaximoFichas) || (cantFichas <= 0)) {
                    System.out.println("La cantidad de fichas debe ser mayor a 0 y menor o igual a " + tamMaximoFichas);
                    System.out.println("Ingrese cantidad de fichas:");
                    cantFichas = teclado.nextInt();

                }
                ok = true;
            } catch (InputMismatchException e) {
                //ok = false;
                System.out.println("Disculpe debe ingresar solo numeros.");
                teclado.next();
            }
        }

        return cantFichas;
    }

    //validacion de int
    public static int validarInt(String mensaje, int min, int max, String errorLetra, String error) {
        int ingreso = 0;
        boolean ok = false;
        while (!ok) {

            try {
                System.out.println(mensaje);
                ingreso = teclado.nextInt();
                if (ingreso >= min && ingreso <= max) {
                    ok = true;
                } else {
                    System.out.println(error);
                }
            } catch (InputMismatchException e) {
                //ok = false;
                System.out.println(errorLetra);
                teclado.next();
            }
        }
        return ingreso;
    }

    //validacion de Srings
    public static String validarString(String mensaje, String error) {
        String texto;
        System.out.println(mensaje);
        texto = teclado.nextLine();
        while (texto.trim().isEmpty()) {
            System.out.print(error);
            System.out.println(mensaje);
            texto = teclado.nextLine();
        }

        return texto;

    }

    public static void imprimirTablero(Sistema sistema) {

        char[][] matTablero = partidaAct.getTablero().getMatrizTablero();
        Boolean panelEsta = partidaAct.getTablero().getPanel() != null;

        int iPanel = (panelEsta) ? partidaAct.getTablero().getPanel().getFila() : -1;
        iPanel = iPanel * 2;
        int jPanel = (panelEsta) ? partidaAct.getTablero().getPanel().getColumna() : -1;
        jPanel = jPanel * 2;
        char[][] matPanel = (panelEsta) ? partidaAct.getTablero().getPanel().getMatrizPanel() : null;

        char[][] matImpresion = new char[((matTablero.length) * 2) + 1][((matTablero.length) * 2) + 1];
        int Ftablero = 0;
        int Ctablero = 0;
        int Fpanel = 0;
        int Cpanel = 0;
        int num = 65;
        boolean agregoDelT = false;
        boolean agregoDelP = false;
        boolean cambioDeFilaConT = false;
        boolean cambioDeFilaConP = false;
        for (int i = 0; i < matImpresion.length; i++) {
            for (int j = 0; j < matImpresion[i].length; j++) {
                if (i % 2 == 0) {
                    if (j % 2 != 0) {
                        if (panelEsta) {
                            if (((i >= (iPanel)) && (i <= (iPanel + ((matPanel.length) * 2)))) && ((j >= (jPanel)) && (j <= (jPanel) + ((matPanel.length) * 2)))) {
                                matImpresion[i][j] = '#';
                            } else {
                                matImpresion[i][j] = '-';
                            }
                        } else {
                            matImpresion[i][j] = '-';
                        }
                    } else {
                        if (panelEsta) {
                            if (((i >= (iPanel)) && (i <= (iPanel + ((matPanel.length) * 2)))) && ((j >= (jPanel)) && (j <= (jPanel) + ((matPanel.length) * 2)))) {
                                matImpresion[i][j] = '#';
                            } else {
                                matImpresion[i][j] = '+';
                            }
                        } else {
                            matImpresion[i][j] = '+';
                        }

                    }
                } else {
                    if (j % 2 == 0) {
                        if (panelEsta) {
                            if (((i >= (iPanel)) && (i <= (iPanel + ((matPanel.length) * 2)))) && ((j >= (jPanel)) && (j <= (jPanel) + ((matPanel.length) * 2)))) {
                                matImpresion[i][j] = '#';
                            } else {
                                matImpresion[i][j] = '|';
                            }
                        } else {
                            matImpresion[i][j] = '|';
                        }
                    } else {
                        if (!(sistema.getPartidaActual().getTablero().isImprimirAsteriscos())) {
                            if (panelEsta) {

                                if (((i >= (iPanel)) && (i <= (iPanel + ((matPanel.length) * 2)))) && ((j >= (jPanel)) && (j <= (jPanel) + ((matPanel.length) * 2)))) {

                                    matImpresion[i][j] = matPanel[Fpanel][Cpanel];
                                    agregoDelP = true;
                                    cambioDeFilaConP = true;
                                } else {
                                    matImpresion[i][j] = matTablero[Ftablero][Ctablero];
                                    agregoDelT = true;
                                    cambioDeFilaConT = true;
                                }
                            } else {
                                matImpresion[i][j] = matTablero[Ftablero][Ctablero];
                                agregoDelT = true;
                                cambioDeFilaConT = true;
                            }
                        } else {
                            matImpresion[i][j] = simetrias[Ftablero][Ctablero];
                            agregoDelT = true;
                            cambioDeFilaConT = true;
                        }

                    }
                }
                if (agregoDelP) {
                    Cpanel = Cpanel + 1;
                    Ctablero = Ctablero + 1;
                }
                if (agregoDelT) {
                    Ctablero = Ctablero + 1;
                }
                agregoDelT = false;
                agregoDelP = false;

            }
            if ((i % 2 != 0) & (cambioDeFilaConP)) {
                Ftablero = Ftablero + 1;
                Fpanel = Fpanel + 1;
            } else {
                if ((i % 2 != 0) & (cambioDeFilaConT)) {
                    Ftablero = Ftablero + 1;
                }
            }

            Ctablero = 0;
            Cpanel = 0;
            cambioDeFilaConT = false;
            cambioDeFilaConP = false;

        }
        for (int i = 0; i < matImpresion.length; i++) {
            if (i == 0) {
                System.out.print("  ");
                for (int k = 1; k <= (matImpresion[i].length / 2); k++) {
                    System.out.print(" " + k);
                }
                System.out.println("");
            }
            if (i % 2 != 0) {
                System.out.print((char) num + " ");
                num = num + 1;
            } else {
                System.out.print("  ");
            }
            for (int j = 0; j < matImpresion[i].length; j++) {
                System.out.print(matImpresion[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");

    }

    public static TipoJugada pedirJugada(Sistema sistema) {
        //modificado para que si ingresan algo de 3 letras que no es valido de un mensaje de error
        if (turnoBlanco) {
            System.out.println("Jugador Blanco alias: "+sistema.getPartidaActual().getJugadorB().getJugador().getAlias()+"  ---> Por favor ingrese jugada");
        } else {
            System.out.println("Jugador Negro alias: "+sistema.getPartidaActual().getJugadorN().getJugador().getAlias()+"  ---> Por favor ingrese jugada");
        }

        TipoJugada tipoJugada = null;
        String jugada = teclado.nextLine().toUpperCase();

        if (jugada.length() == 1) {
            //abandonar
            if (jugada.equalsIgnoreCase("X")) {
                tipoJugada = new TipoJugada("abandonar", -1, -1, -1, -1);
            } else {
                tipoJugada = new TipoJugada("not valid", -1, -1, -1, -1);
            }
        } else {
            if (jugada.length() == 3) {

                char tipo = jugada.charAt(0);
                char letra;
                int fila;
                int col;
                switch (tipo) {
                    case 'J':
                        letra = jugada.charAt(1);
                        fila = ((int) letra) - (65);
                        col = jugada.charAt(2) - 48 - 1;
                        tipoJugada = new TipoJugada("ficha", fila, col, -1, -1);
                        break;

                    case 'P':
                        letra = jugada.charAt(1);
                        fila = ((int) letra) - (65);
                        col = jugada.charAt(2) - 48 - 1;
                        tipoJugada = new TipoJugada("panel", fila, col, -1, -1);
                        break;

                    case 'M':
                        int cantMovimientos = jugada.charAt(2) - 48;
                        char direccion = jugada.charAt(1);
                        switch (direccion) {
                            case 'I':
                                tipoJugada = new TipoJugada("izq", -1, -1, 0, cantMovimientos);
                                break;
                            case 'D':
                                tipoJugada = new TipoJugada("der", -1, -1, 0, cantMovimientos);
                                break;
                            case 'A':
                                tipoJugada = new TipoJugada("arr", -1, -1, cantMovimientos, 0);
                                break;
                            case 'B':
                                tipoJugada = new TipoJugada("aba", -1, -1, cantMovimientos, 0);
                                break;
                            default:
                                tipoJugada = new TipoJugada("not valid", -1, -1, -1, -1);
                        }
                        break;

                    default:
                        tipoJugada = new TipoJugada("not valid", -1, -1, -1, -1);
                        break;
                }
            } else {
                tipoJugada = new TipoJugada("not valid", -1, -1, -1, -1);
            }
        }
        return tipoJugada;
    }

}
