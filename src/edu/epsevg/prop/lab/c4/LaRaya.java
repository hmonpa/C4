package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;

/**
 * Jugador Connecta4 creado con IA
 * @author Héctor Montesinos, Alberto López Rey
 */

public class LaRaya implements Jugador, IAuto {
  private String nom;
  private int depth;
  private int cont;
  
  /**
   * Constructor de nuestro jugador
   * @param depth 
   */
  public LaRaya(int depth)
  {
    nom = "La Raya";
    this.depth = depth;
  }
  
  /**
   * Devuelve el nombre del jugador.
   * @return nom
   */
  public String nom()
  {
    return nom;
  }
  
  /** 
   * Llama a la función inici_minimax.
   * @param t
   * @param color
   * @return inici_minmax(t,depth,color)
   */
  @Override
  public int moviment(Tauler t, int color)
  {
      return inici_minmax(t, depth, color);   
  }
  
  /**
   * Escoge la mejor columna o mejor movimiento, según la mejor h obtenida (llamando a la función min). Devuelve la mejor columna.
   * @param estat   (Tablero)
   * @param depth   (Profundidad)
   * @param player  (Jugador)
   * @return col    (Mejor columna)
   */
  private int inici_minmax(Tauler estat, int depth, int player){
      int valor, col = 0;
      cont = 0;
      int max = Integer.MIN_VALUE;
      System.out.println("Explorando: ");
      for(int i = 0; i < estat.getMida(); ++i){
         Tauler estat2 = new Tauler(estat);
         if(estat.movpossible(i)){
             estat2.afegeix(i, player); 
             cont++;
             //estat2.pintaTaulerALaConsola();
             //System.out.println("-----------------");
             valor = -max(estat2, depth, -player, Integer.MIN_VALUE, Integer.MAX_VALUE);
             System.out.println("Columna " + i + ". Valor heurístico: " + valor);
             if (max < valor){
               max = valor;
               col = i;
             }
         }
      }
      System.out.println("Jugadas exploradas " + cont);
      
      //if(max > 214000000) System.out.println("Columna: " + col + "; Valor heuristic ∞");
      //else if (max < -214000000) System.out.println("Columna: " + col + "; Valor heuristic -∞");
      //else 
      System.out.println("Mejor opción:");
      System.out.println("Columna " + col + ". Valor heuristico " + max);
      System.out.println("--------------------------------------");
      return col;
  }
  
  /**
   * Maximiza el valor del estado.
   * @param estat   (Tablero)
   * @param depth   (Profundidad)
   * @param player  (Jugador)
   * @param alpha   (α)
   * @param beta    (β)
   * @return alpha
   */
  private int max(Tauler estat, int depth, int player, int alpha, int beta){
      // Max
      if (!estat.espotmoure() || depth == 0 ) return heuristica(estat, player);
      for (int i=0;i<estat.getMida();i++){    
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              cont++;
              estat2.afegeix(i, player);
              if (estat2.solucio(i, player)) return Integer.MAX_VALUE;
              //estat2.pintaTaulerALaConsola();
              int valor = min(estat2, depth-1, -player, alpha, beta);    
              alpha = Math.max(alpha, valor);
              if (beta <= alpha){
                  return alpha;
              }
          }
      }
      return alpha;
  }
  
  /**
   * Minimiza el valor del estado.
   * @param estat   (Tablero)
   * @param depth   (Profundidad)
   * @param player  (Jugador)
   * @param alpha   (α)
   * @param beta    (β)
   * @return beta
   */
  private int min(Tauler estat, int depth, int player, int alpha, int beta){
      // Min
      if (depth == 0 || !estat.espotmoure()) return heuristica(estat, player);
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player); 
              cont++;
              if (estat2.solucio(i, player)) return Integer.MIN_VALUE;
              //estat2.pintaTaulerALaConsola();
              int valor = max(estat2, depth-1, -player, alpha, beta);    
              beta = Math.min(beta, valor);
              if (beta <= alpha){
                  return beta;
              }
          }
      }
      return beta;
  }
  
  /**
   * Recorre todas las fichas del tablero, dependiendo si el color coincide con uno o con otro
   * Este recorrido devuelve el número de fichas seguidas, el cual se le pasa a num_heuristic
   * Devuelve la diferencia de los valores heurísticos de ambos jugadores.
   * @param t       (Tablero)
   * @param color   (Jugador)
   * @return        HeurísticaJugador1-HeurísticaJugador2
   */
  private int heuristica (Tauler t, int color){
    int heu_laraya = 0;
    int heu_enemic = 0;
    int num = 0;
    for (int i=0; i<t.getMida(); i++){
        for (int col=0; col<t.getMida(); col++) {    
            if (t.getColor(i, col) == color){           // LaRaya
                num = recorre(t,color,i,col);
                heu_laraya += num_heuristic(num,color,color,heu_laraya);
            }
            else if(t.getColor(i, col) == -color){      // Adversario
                 num = recorre(t,-color,i,col);
                 heu_enemic += num_heuristic(num,-color,color,heu_enemic);
            }
        }              
    }
    return heu_laraya - heu_enemic;
  }
  /** 
   * Realiza recorridos del tablero y cuenta las fichas adyacentes.
   * @param t       (Tablero)
   * @param color   (Jugador: Positivo = LaRaya / Negativo = Adversario)
   * @param X       (Fila)
   * @param Y       (Columna)
   * @return        SUM(seguides)
   */
  private int recorre(Tauler t, int color, int X, int Y){
      int seguides_h = 1;
      int seguides_v = 1;
      int seguides_dc = 1;
      int seguides_dd = 1;

  //--------------------------- Vertical ---------------------------
      if (Y-1 != -1){                                       // No sale del tablero por abajo
          if(t.getColor(X, Y-1) != 0){
              ++seguides_v;
              if (Y-2 != -1){                               
                    if(t.getColor(X, Y-2) == color){
                        ++seguides_v;
                    } 
               }
          }
      }
      if (Y+1 != t.getMida()){                              // No sale del tablero por arriba
          if(t.getColor(X, Y+1) != 0){
              ++seguides_v;
              if (Y+2 != t.getMida()){                      
                    if(t.getColor(X, Y+2) == color){
                        ++seguides_v;
                    } 
              }
          }
      }
 //-------------------------- Horizontal --------------------------
      if (X-1 != -1){                                       // No sale del tablero por la izq.
          if(t.getColor(X-1, Y) == color){
              if(Y == 1) seguides_h = seguides_h + 1 * 2;
              else ++seguides_h;
              if (X-2 != -1){                               
                    if(t.getColor(X-2, Y) == color){
                        if(Y == 1) seguides_h = seguides_h + 1 * 2;
                        else ++seguides_h;
                    } 
               }
          }
      }
      if (X+1 != t.getMida()){                              // No sale del tablero por la der.
          if(t.getColor(X+1, Y) == color){
              if(Y == 1) seguides_h = seguides_h + 1 * 2;
              else ++seguides_h;
              if (X+2 != t.getMida()){                      
                    if(t.getColor(X+2, Y) == color){
                        if(Y == 1) seguides_h = seguides_h + 1 * 2;
                        else ++seguides_h;
                    } 
               }
          }
      }

 //---------------------- Diagonal creciente ----------------------
      if (X-1 != -1 && Y-1 != -1){                         // No sale del tablero por la diagonal inferior izq.
          if(t.getColor(X-1, Y-1) == color){
              ++seguides_dc;
              if (X-2 != -1 && Y-2 != -1){              
                    if(t.getColor(X-2, Y-2) == color){
                        ++seguides_dc;
                    } 
               }
          }
      }
      if (X+1 != t.getMida() && Y+1 != t.getMida()){        // No sale del tablero por la diagonal superior der.
          if(t.getColor(X+1, Y+1) == color){
              ++seguides_dc;
                if (X+2 != t.getMida() && Y+2 != t.getMida()){
                    if(t.getColor(X+2, Y+2) == color){
                        ++seguides_dc;
                    } 
               }
          }
      }
 //--------------------- Diagonal decreciente ---------------------
      if (X-1 != -1 && Y+1 != t.getMida()){                // No sale del tablero por la diagonal inferior der.
          if(t.getColor(X-1, Y+1) == color){
              ++seguides_dd;
              if (X-2 != -1 && Y+2 != t.getMida()){
                    if(t.getColor(X-2, Y+2) == color){
                        ++seguides_dd;
                    } 
               }
          }
      }
      if (X+1 != t.getMida() && Y-1 != -1){                 // No sale del tablero por la diagonal superior izq.
          if(t.getColor(X+1, Y-1) == color){
              ++seguides_dd;
               if (X+2 != t.getMida() && Y-2 != -1){
                    if(t.getColor(X+2, Y-2) == color){
                        ++seguides_dd;
                    } 
               }
          }
      }
      
    return seguides_v + seguides_h + seguides_dd + seguides_dc;
  }
  
  /**
   * Calcula el valor heurístico utilizando la siguiente fórmula:
   *    ValorHeurístico + (-1000) * (Jugador1*Jugador2) * FichasAdyacentes.
   * @param num         (Fichas adyacentes)
   * @param color       (Jugador 1)
   * @param color_comp  (Jugador 2)
   * @param heu_actual  (Valor heurístico)
   * @return            Fórmula
   */
  private int num_heuristic(int num, int color, int color_comp, int heu_actual){  
      return heu_actual + (-1000) *(color * color_comp) * num ;
  }
}