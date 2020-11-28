package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;
import static java.lang.Math.abs;
        
/**
 * @author hector, alberto
 */

public class LaRaya implements Jugador, IAuto {
  private String nom;
  private int depth;
  int cont = 0;
  
  public LaRaya(int depth)
  {
    nom = "La Raya";
    this.depth = depth;
  }
  
  @Override
  public int moviment(Tauler t, int color)
  {
      return inici_minmax(t, depth, color, cont);   
  }
  
  public String nom()
  {
    return nom;
  }
  
  private int inici_minmax(Tauler estat, int depth, int player, int cont){
      int valor, col = 0;
      int max = Integer.MIN_VALUE;
      for(int i = 0; i < estat.getMida(); ++i){
         if(estat.movpossible(i)){
             Tauler estat2 = new Tauler(estat);
             estat2.afegeix(i, player); 
             //System.out.println("=========>pintar_first"+i);
             System.out.println("Exploració " + cont);
             cont++;
             //estat2.pintaTaulerALaConsola();
             System.out.println("-----------------");
             valor = min(estat2, depth-1, -player, Integer.MIN_VALUE, Integer.MAX_VALUE, estat2.solucio(i, player));
             //System.out.println("Columna >" + i + " valor heurístic: "+valor);
             if (max < valor){
               max = valor;
               col = i;
             }else if(max == valor){
                if (abs(col-3) > abs(i-3)){
                    col = i;
                }
             }
         }
      }
      System.out.println("Jugadas exploradas en este iteración " + cont);
      
      if(max > 214000000) System.out.println("Columna: " + col + "; Valor heuristic ∞");
      else if (max < -214000000) System.out.println("Columna: " + col + "; Valor heuristic -∞");
      else System.out.println("Columna: " + col + "; Valor heuristic " + max);
      //System.out.println(col);
      return col;
  }

  private int max(Tauler estat, int depth, int player, int alpha, int beta, boolean solucio){
      // Max
       if (solucio){
          return Integer.MIN_VALUE;
      }if(depth == 0){
          return heuristica(estat, player);
      }
      int valor = Integer.MIN_VALUE;
      for (int i=0;i<estat.getMida();i++){    
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);
              //System.out.println("=========>pintar_max"+i);
              //estat2.pintaTaulerALaConsola();
              valor = min(estat2, depth-1, -player, alpha, beta, estat2.solucio(i, player));    
              alpha = Math.max(alpha, valor);
              if (beta <= alpha) return valor;
          }
      }
      return valor;
  }
  
  private int min(Tauler estat, int depth, int player, int alpha, int beta, boolean solucio){
      // Min
      if (solucio){
          return Integer.MAX_VALUE;
      }if(depth == 0){
          return heuristica(estat, -player);
      }
      int valor = Integer.MAX_VALUE;
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player); 
              //System.out.println("=========>pintar_min"+i);
              //estat2.pintaTaulerALaConsola();// player pendiente de asegurar implementación
              valor = max(estat2, depth-1, -player, alpha, beta,estat2.solucio(i, player));    
              beta = Math.min(beta, valor);
              if (beta <= alpha) return valor;
          }
      }
      return valor;
  }
  
  private int heuristica (Tauler t, int color){
    int heu_laraya = 0;
    int heu_enemic = 0;
    int num=0;
    for (int i=0; i<t.getMida(); i++){
        for (int col=0; col<t.getMida(); col++) {    
            if (t.getColor(i, col) == color){           // LaRaya
                num = recorre(t,color,i,col);
                heu_laraya += num_heuristic(num,color,color,heu_laraya);
                return heu_laraya;
            }
            else if(t.getColor(i, col) == -color){      // Adversario
                 num = recorre(t,-color,i,col);
                 heu_enemic += num_heuristic(num,-color,color,heu_enemic);
                 return heu_enemic;
            }
        }              
    }
    int heu = heu_laraya - heu_enemic;
    return heu;
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
             /* if (Y-2 != -1){                               
                    if(t.getColor(X, Y-2) == color){
                        ++seguides_v;
                    } 
               }*/
          }
      }
      if (Y+1 != t.getMida()){                              // No sale del tablero por arriba
          if(t.getColor(X, Y+1) != 0){
              ++seguides_v;
             /* if (Y+2 != t.getMida()){                      
                    if(t.getColor(X, Y+2) == color){
                        ++seguides_v;
                    } 
               }*/
          }
      }
 //---------------HORIZONTAL-------------------
      if (X-1 != -1){                                       // No sale del tablero por la izq.
          if(t.getColor(X-1, Y) == color){
              ++seguides_h;
              /*if (X-2 != -1){                               
                    if(t.getColor(X-2, Y) == color){
                        ++seguides_h;
                    } 
               }*/
          }
      }
      if (X+1 != t.getMida()){                              // No sale del tablero por la der.
          if(t.getColor(X+1, Y) == color){
              ++seguides_h;
              /*if (X+2 != t.getMida()){                      
                    if(t.getColor(X+2, Y) == color){
                        ++seguides_h;
                    } 
               }*/
          }
      }

 //-----------------DIAGONAL_CREIXENT--------------
      if (X-1 != -1 && Y-1 != -1){                         // No sale del tablero por la diagonal inferior izq.
          if(t.getColor(X-1, Y-1) == color){
              ++seguides_dc;
              /*if (X-2 != -1 && Y-2 != -1){              
                    if(t.getColor(X-2, Y-2) == color){
                        ++seguides_dc;
                    } 
               }*/
          }
      }
      if (X+1 != t.getMida() && Y+1 != t.getMida()){        // No sale del tablero por la diagonal superior der.
          if(t.getColor(X+1, Y+1) == color){
              ++seguides_dc;
             /* if (X+2 != t.getMida() && Y+2 != t.getMida()){
                    if(t.getColor(X+2, Y+2) == color){
                        ++seguides_dc;
                    } 
               }*/
          }
      }
//--------------------DIAGONAL_DECREIXENT--------------------------
      if (X-1 != -1 && Y+1 != t.getMida()){                // No sale del tablero por la diagonal inferior der.
          if(t.getColor(X-1, Y+1) == color){
              ++seguides_dd;
              /*if (X-2 != -1 && Y+2 != t.getMida()){
                    if(t.getColor(X-2, Y+2) == color){
                        ++seguides_dd;
                    } 
               }*/
          }
      }
      if (X+1 != t.getMida() && Y-1 != -1){                 // No sale del tablero por la diagonal superior izq.
          if(t.getColor(X+1, Y-1) == color){
              ++seguides_dd;
              /*if (X+2 != t.getMida() && Y-2 != -1){
                    if(t.getColor(X+2, Y-2) == color){
                        ++seguides_dd;
                    } 
               }*/
          }
      }
    int max = Integer.max(seguides_h,seguides_v);
    max = Integer.max(max,seguides_dc);
    return Integer.max(max,seguides_dd);
  }
  
  private int num_heuristic(int num, int color,int color_comp,int heu_actual){
      
      /*if (X == 0) X = 1;
      if (X == 1) X = 2;
      if (X == 2) X = 3;
      if (X == 3) X = 4;
      if (X == 4) X = 4;
      if (X == 5) X = 3;
      if (X == 6) X = 2;
      if (X == 7) X = 1;*/

      if (num == 1){
          heu_actual += heu_actual + 100 *(color * color_comp);
      }
      if (num == 2){
          heu_actual += heu_actual + 300 *(color * color_comp);
      }
      if (num == 3){
          heu_actual += heu_actual + 500 *(color * color_comp);
      }
      if (num == 4){
          heu_actual += heu_actual + 10000 *(color * color_comp);
      }
      return heu_actual;
  }
}