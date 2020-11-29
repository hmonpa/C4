package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;


/**
 * @author Héctor Montesinos, Alberto López Rey
 */

public class LaRaya implements Jugador, IAuto {
  private String nom;
  private int depth;
  private int cont;
  
  /**
   * LaRaya:
   *    Constructor de nuestro jugador
   * @param depth 
   */
  public LaRaya(int depth)
  {
    nom = "La Raya";
    this.depth = depth;
  }
  
  // nom: 
  //    Devuelve el nombre del jugador
  public String nom()
  {
    return nom;
  }
  
  /**
   * moviment: 
   *    Llama a la función inici_minimax 
   * @param t
   * @param color
   * @return inici_minmax(t,depth,color): Especificación más abajo.
   */
  @Override
  public int moviment(Tauler t, int color)
  {
      return inici_minmax(t, depth, color);   
  }
  
  /**
   * inici_minmax
   *    Escoge la mejor columna o mejor movimiento, según la mejor h obtenida (llamando a la función min). Devuelve la mejor columna.
   * @param estat   (Tablero)
   * @param depth   (Profundidad)
   * @param player  (Jugador)
   * @return col
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
             /*else if(max == valor){
                if (abs(col-3) > abs(i-3)){
                    col = i;
                }
             }*/
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
   * max:
   *    Maximiza el valor del estado
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
   * min:
   *    Minimiza el valor del estado
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
   * Función heuristica:
   *    Recorre todas las fichas del tablero, dependiendo si el color coincide con uno o con otro
   *    Este recorrido devuelve el número de fichas seguidas, el cual se le pasa a num_heuristic
   *    Devuelve el valor heurístico para cada jugador
   * @param t       (Tablero)
   * @param color   (Player)
   * @return  
   */
  private int heuristica (Tauler t, int color){
    int heu_laraya = 0;
    int heu_enemic = 0;
    int num=0;
    for (int i=0; i<t.getMida(); i++){
        for (int col=0; col<t.getMida(); col++) {    
            if (t.getColor(i, col) == color){           // LaRaya
                num = recorre(t,color,i,col);
                heu_laraya += num_heuristic(num,color,color,heu_laraya);
                //return heu_laraya;
            }
            else if(t.getColor(i, col) == -color){      // Adversario
                 num = recorre(t,-color,i,col);
                 heu_enemic += num_heuristic(num,-color,color,heu_enemic);
                 //return heu_enemic;
            }
        }              
    }
    return heu_laraya - heu_enemic;
  }
  private int recorre(Tauler t, int color, int X, int Y){
      int seguides_h = 1;
      int seguides_v = 1;
      int seguides_dc = 1;
      int seguides_dd = 1;

  //---------------VERTICAL-----------------------
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
 //---------------HORIZONTAL-------------------
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

 //-----------------DIAGONAL_CREIXENT--------------
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
//--------------------DIAGONAL_DECREIXENT--------------------------
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
  
  private int num_heuristic(int num, int color,int color_comp,int heu_actual){
      
      return heu_actual + 100000000 *(color * color_comp) * num ;
  }
}