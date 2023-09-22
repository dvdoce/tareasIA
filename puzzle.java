package IA;

import java.util.*;


//Clase que representa un estado del juego
class Estado {
 int[][] tablero;
 Estado padre;

 public Estado(int[][] tablero, Estado padre) {
     this.tablero = tablero;
     this.padre = padre;
 }

 @Override
 public boolean equals(Object obj) {
     if (this == obj)
         return true;
     if (obj == null || getClass() != obj.getClass())
         return false;
     Estado estado = (Estado) obj;
     return Arrays.deepEquals(tablero, estado.tablero);
 }

 @Override
 public int hashCode() {
     return Arrays.deepHashCode(tablero);
 }
}

public class puzzle {

 // Representación del estado inicial y objetivo del 8 Puzzle
 static int[][] estadoInicial = {{2, 4, 6}, {5, 0, 7}, {8, 1, 3}};
 static int[][] estadoObjetivo = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

 // Función para imprimir un estado en un formato legible
 static void imprimirEstado(int[][] estado) {
     for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
             if (estado[i][j] == 0) {
                 System.out.print("  | ");
             } else {
                 System.out.print(estado[i][j] + " | ");
             }
         }
         System.out.println();
         System.out.println("---------");
     }
 }

 // Función para encontrar la posición de 0 (el espacio en blanco) en el estado
 static int[] encontrarPosicion(int[][] estado) {
     int[] posicion = new int[2];
     for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
             if (estado[i][j] == 0) {
                 posicion[0] = i;
                 posicion[1] = j;
                 return posicion;
             }
         }
     }
     return null;
 }

 // Operadores de movimiento: izquierda, derecha, arriba, abajo
 static int[][] moverIzquierda(int[][] estado) {
     int[] posicion = encontrarPosicion(estado);
     int i = posicion[0];
     int j = posicion[1];
     if (j > 0) {
         int[][] nuevoEstado = new int[3][3];
         for (int x = 0; x < 3; x++) {
             nuevoEstado[x] = estado[x].clone();
         }
         nuevoEstado[i][j] = estado[i][j - 1];
         nuevoEstado[i][j - 1] = 0;
         return nuevoEstado;
     }
     return null;
 }

 static int[][] moverDerecha(int[][] estado) {
     int[] posicion = encontrarPosicion(estado);
     int i = posicion[0];
     int j = posicion[1];
     if (j < 2) {
         int[][] nuevoEstado = new int[3][3];
         for (int x = 0; x < 3; x++) {
             nuevoEstado[x] = estado[x].clone();
         }
         nuevoEstado[i][j] = estado[i][j + 1];
         nuevoEstado[i][j + 1] = 0;
         return nuevoEstado;
     }
     return null;
 }

 static int[][] moverArriba(int[][] estado) {
     int[] posicion = encontrarPosicion(estado);
     int i = posicion[0];
     int j = posicion[1];
     if (i > 0) {
         int[][] nuevoEstado = new int[3][3];
         for (int x = 0; x < 3; x++) {
             nuevoEstado[x] = estado[x].clone();
         }
         nuevoEstado[i][j] = estado[i - 1][j];
         nuevoEstado[i - 1][j] = 0;
         return nuevoEstado;
     }
     return null;
 }

 static int[][] moverAbajo(int[][] estado) {
     int[] posicion = encontrarPosicion(estado);
     int i = posicion[0];
     int j = posicion[1];
     if (i < 2) {
         int[][] nuevoEstado = new int[3][3];
         for (int x = 0; x < 3; x++) {
             nuevoEstado[x] = estado[x].clone();
         }
         nuevoEstado[i][j] = estado[i + 1][j];
         nuevoEstado[i + 1][j] = 0;
         return nuevoEstado;
     }
     return null;
 }

 // Función para verificar si dos estados son iguales
 static boolean sonIguales(int[][] estado1, int[][] estado2) {
     return Arrays.deepEquals(estado1, estado2);
 }

 // Algoritmo de búsqueda por anchura
 static int[][] busquedaPorAnchura(int[][] estadoInicial, int[][] estadoObjetivo) {
     Queue<Estado> frontera = new LinkedList<>();
     Set<Estado> explorados = new HashSet<>();
     Estado estadoInicialObj = new Estado(estadoInicial, null);
     frontera.add(estadoInicialObj);

     while (!frontera.isEmpty()) {
         Estado estadoActual = frontera.poll();
         explorados.add(estadoActual);

         if (sonIguales(estadoActual.tablero, estadoObjetivo)) {
             return estadoActual.tablero;
         }

         int[][][] movimientos = {moverIzquierda(estadoActual.tablero), moverDerecha(estadoActual.tablero),
                 moverArriba(estadoActual.tablero), moverAbajo(estadoActual.tablero)};

         for (int[][] movimiento : movimientos) {
             if (movimiento != null) {
                 Estado nuevoEstado = new Estado(movimiento, estadoActual);
                 if (!explorados.contains(nuevoEstado) && !frontera.contains(nuevoEstado)) {
                     frontera.add(nuevoEstado);
                 }
             }
         }
     }
     return null;
 }

 // Algoritmo de búsqueda por profundidad
 static int[][] busquedaPorProfundidad(int[][] estadoInicial, int[][] estadoObjetivo) {
     Stack<Estado> frontera = new Stack<>();
     Set<Estado> explorados = new HashSet<>();
     Estado estadoInicialObj = new Estado(estadoInicial, null);
     frontera.push(estadoInicialObj);

     while (!frontera.isEmpty()) {
         Estado estadoActual = frontera.pop();
         explorados.add(estadoActual);

         if (sonIguales(estadoActual.tablero, estadoObjetivo)) {
             return estadoActual.tablero;
         }

         int[][][] movimientos = {moverIzquierda(estadoActual.tablero), moverDerecha(estadoActual.tablero),
                 moverArriba(estadoActual.tablero), moverAbajo(estadoActual.tablero)};

         for (int[][] movimiento : movimientos) {
             if (movimiento != null) {
                 Estado nuevoEstado = new Estado(movimiento, estadoActual);
                 if (!explorados.contains(nuevoEstado) && !frontera.contains(nuevoEstado)) {
                     frontera.push(nuevoEstado);
                 }
             }
         }
     }
     return null;
 }

 // Algoritmo de búsqueda por costo uniforme
 static int[][] busquedaPorCostoUniforme(int[][] estadoInicial, int[][] estadoObjetivo) {
     PriorityQueue<Estado> frontera = new PriorityQueue<>(new Comparator<Estado>() {
         public int compare(Estado estado1, Estado estado2) {
             // Compara los estados basados en el costo total hasta el momento
             return Integer.compare(calcularCosto(estado1), calcularCosto(estado2));
         }
     });
     Map<Estado, Integer> costo = new HashMap<>();
     Estado estadoInicialObj = new Estado(estadoInicial, null);
     frontera.add(estadoInicialObj);
     costo.put(estadoInicialObj, 0);

     while (!frontera.isEmpty()) {
         Estado estadoActual = frontera.poll();

         if (sonIguales(estadoActual.tablero, estadoObjetivo)) {
             return estadoActual.tablero;
         }

         int[][][] movimientos = {moverIzquierda(estadoActual.tablero), moverDerecha(estadoActual.tablero),
                 moverArriba(estadoActual.tablero), moverAbajo(estadoActual.tablero)};

         for (int[][] movimiento : movimientos) {
             if (movimiento != null) {
                 Estado nuevoEstado = new Estado(movimiento, estadoActual);
                 int nuevoCosto = costo.get(estadoActual) + 1;
                 if (!costo.containsKey(nuevoEstado) || nuevoCosto < costo.get(nuevoEstado)) {
                     costo.put(nuevoEstado, nuevoCosto);
                     frontera.add(nuevoEstado);
                 }
             }
         }
     }
     return null;
 }

 // Función para calcular el costo total de un estado
 static int calcularCosto(Estado estado) {
     int costo = 0;
     while (estado != null) {
         costo++;
         estado = estado.padre;
     }
     return costo - 1;
 }

 public static void main(String[] args) {
     System.out.println("Estado Inicial:");
     imprimirEstado(estadoInicial);

     System.out.println("\nBusqueda por Anchura:");
     int[][] solucionAnchura = busquedaPorAnchura(estadoInicial, estadoObjetivo);
     if (solucionAnchura != null) {
         imprimirEstado(solucionAnchura);
     }

     System.out.println("\nBusqueda por Profundidad:");
     int[][] solucionProfundidad = busquedaPorProfundidad(estadoInicial, estadoObjetivo);
     if (solucionProfundidad != null) {
         imprimirEstado(solucionProfundidad);
     }

     System.out.println("\nBusqueda por Costo Uniforme:");
     int[][] solucionCostoUniforme = busquedaPorCostoUniforme(estadoInicial, estadoObjetivo);
     if (solucionCostoUniforme != null) {
         imprimirEstado(solucionCostoUniforme);
     }
 }
}