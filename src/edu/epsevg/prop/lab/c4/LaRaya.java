package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;
import static java.lang.Math.abs;
        
/**
 * @author hector, alberto
 */

public class LaRaya implements Jugador, IAuto {
  private String nom;
  
  public LaRaya()
  {
    nom = "La Raya";
  }
  
  public int moviment(Tauler t, int color)
  {
      return inici_minmax(t,8,color);
  }
  
  public String nom()
  {
    return nom;
  }
  
  private int inici_minmax(Tauler estat,int depth, int player){
      int valor, fila = 0;
      int max = Integer.MIN_VALUE;
      for(int i = 0; i < estat.getMida(); ++i){
         if(estat.movpossible(i)){
             Tauler estat2 = new Tauler(estat);
             estat2.afegeix(i, player); 
             //System.out.println("=========>pintar_first"+i);
             //estat2.pintaTaulerALaConsola();
             valor = min(estat2, depth-1, -player, Integer.MIN_VALUE, Integer.MAX_VALUE, estat2.solucio(i, player));
             System.out.println("+++++>"+valor);
             if (max < valor){
               max = valor;
               fila = i;
             }else if(max == valor){
                if (abs(fila-3) > abs(i-3)){
                    fila = i;
                }
             }
         }
      }
      System.out.println("=>"+fila+"=>"+max);
      return fila;
  }
  // player es el color
  private int max(Tauler estat, int depth, int player, int alpha, int beta, boolean solucio){
      // Max
       if (solucio){
          // System.out.println("papaaaaa ouuuuuu");
          return Integer.MIN_VALUE;
      }if(depth == 0){
          return heuristica(estat, player);
      }
      int valor = Integer.MIN_VALUE;
      //if(depth==7)System.out.println("Max >"+depth);
      for (int i=0;i<estat.getMida();i++){    
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);
              //System.out.println("=========>pintar_max"+i);
              //estat2.pintaTaulerALaConsola();// player pendiente de asegurar implementación
              valor = min(estat2, depth-1, -player, alpha, beta, estat2.solucio(i, player));    // pdte implementar min
              alpha = Math.max(alpha, valor);
              if (beta <= alpha) return valor;
          }
      }
      return valor;
  }
  
    // player es el color
  private int min(Tauler estat, int depth, int player, int alpha, int beta,boolean solucio){
      // Min
      if (solucio){
          return Integer.MAX_VALUE;
      }if(depth == 0){
          return heuristica(estat, -player);
      }
      int valor = Integer.MAX_VALUE;
      //if(depth==7)System.out.println("Min >"+depth);
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player); 
              //System.out.println("=========>pintar_min"+i);
              //estat2.pintaTaulerALaConsola();// player pendiente de asegurar implementación
              valor = max(estat2, depth-1, -player, alpha, beta,estat2.solucio(i, player));    // pdte implementar min
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
    for (int i=0;i<t.getMida();i++){
        for (int col = 0; col <t.getMida(); ++col) {
            if (t.getColor(i, col) == color){
                num = recorre(t,color,i,col);
                heu_laraya = num_heuristic(num,color,color,heu_laraya,i);
            }
            else if(t.getColor(i, col) == -color){
                 num = recorre(t,-color,i,col);
                 heu_enemic = num_heuristic(num,-color,color,heu_enemic,i);
            
            }
        }
                    
    }
    return heu_laraya - heu_enemic;
  }
  private int recorre(Tauler t, int color, int X,int Y){
      int seguides_h = 1;
      int seguides_v = 1;
      int seguides_dc = 1;
      int seguides_dd = 1;
 //---------------HORIZONTAL-------------------
      if (X-1 != -1){
          if(t.getColor(X-1, Y) == color){
              ++seguides_h;
              if (X-2 != -1){
                    if(t.getColor(X-2, Y) == color){
                        ++seguides_h;
                    } 
               }
          }
      }
      if (X+1 != t.getMida()){
          if(t.getColor(X+1, Y) == color){
              ++seguides_h;
              if (X+2 != t.getMida()){
                    if(t.getColor(X+2, Y) == color){
                        ++seguides_h;
                    } 
               }
          }
      }
 //---------------VERTICAL-----------------------
      if (Y-1 != -1){
          if(t.getColor(X, Y-1) == color){
              ++seguides_v;
              if (Y-2 != -1){
                    if(t.getColor(X, Y-2) == color){
                        ++seguides_v;
                    } 
               }
          }
      }
      if (Y+1 != t.getMida()){
          if(t.getColor(X, Y+1) == color){
              ++seguides_v;
              if (Y+2 != t.getMida()){
                    if(t.getColor(X, Y+2) == color){
                        ++seguides_v;
                    } 
               }
          }
      }
 //-----------------DIAGONAL_CREIXENT------------------------------
       if (X-1 != -1 && Y-1 != -1){
          if(t.getColor(X-1, Y-1) == color){
              ++seguides_dc;
              if (X-2 != -1 && Y-2 != -1){
                    if(t.getColor(X-2, Y-2) == color){
                        ++seguides_dc;
                    } 
               }
          }
      }
      if (X+1 != t.getMida() && Y+1 != t.getMida()){
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
       if (X-1 != -1 && Y+1 != t.getMida()){
          if(t.getColor(X-1, Y+1) == color){
              ++seguides_dd;
              if (X-2 != -1 && Y+2 != t.getMida()){
                    if(t.getColor(X-2, Y+2) == color){
                        ++seguides_dd;
                    } 
               }
          }
      }
      if (X+1 != t.getMida() && Y-1 != -1){
          if(t.getColor(X+1, Y-1) == color){
              ++seguides_dd;
              if (X+2 != t.getMida() && Y-2 != -1){
                    if(t.getColor(X+2, Y-2) == color){
                        ++seguides_dd;
                    } 
               }
          }
      }
    int max = Integer.max(seguides_h,seguides_v);
    max = Integer.max(max,seguides_dc);
    return Integer.max(max,seguides_dd);
  }
  private int num_heuristic(int num, int color,int color_comp,int heu_actual,int X){
      int heu = 0;
      
      if (X == 0) X = 1;
      if (X == 1) X = 2;
      if (X == 2) X = 3;
      if (X == 3) X = 4;
      if (X == 4) X = 4;
      if (X == 5) X = 3;
      if (X == 6) X = 2;
      if (X == 7) X = 1;

      if (num == 1){
          heu = heu_actual + 100 *(color * color_comp);
      }
      if (num == 2){
          heu = heu_actual + 500 *(color * color_comp);
      }
      if (num == 3){
          heu = heu_actual + 10000 *(color * color_comp);
      }
      return heu;
  }
}