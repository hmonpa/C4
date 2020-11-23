package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;
import java.util.ArrayList;
        
/**
 * @author hector
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
      int LNfirst[] = new int[estat.getMida()];
      for(int i = 0; i < estat.getMida(); ++i){
         if(estat.movpossible(i)){
             Tauler estat2 = new Tauler(estat);
             estat2.afegeix(i, player); 
             LNfirst[i] = min(estat2, depth-1, player, 0, 0, estat2.solucio(i, player));
         }
      }
      int max = LNfirst[1];
      int fila = 0;
      for(int i = 1; i < estat.getMida(); ++i){
          if (max < LNfirst[i]){
               max = LNfirst[i];
               fila = i;
          }
      }
      return fila;
  }
  // player es el color
  private int max(Tauler estat, int depth, int player, int alpha, int beta, boolean solucio){
      // Max
      if (solucio || depth == 0){
          // return heuri(estat, p1)
      }
      int valor = -100000;
      solucio = false;
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);        // player pendiente de asegurar implementación
              if (estat2.solucio(i, player)){   // player pendiente de asegurar implementación
                  solucio = true;
              }
              valor = min(estat2, depth-1, player, alpha, beta, solucio);    // pdte implementar min
              alpha = Math.max(alpha, valor);
              if (beta <= alpha) return valor;
          }
      }
      return valor;
  }
  
    // player es el color
  private int min(Tauler estat, int depth, int player, int alpha, int beta,boolean solucio){
      // Max
      if (solucio || depth == 0){
          // return heuri(estat, p1)
      }
      int valor = +100000;
      solucio = false;
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);        // player pendiente de asegurar implementación
              if (estat2.solucio(i, player)){   // player pendiente de asegurar implementación
                  solucio = true;
              }
              valor = max(estat2, depth-1, player, alpha, beta,solucio);    // pdte implementar min
              beta = Math.min(beta, valor);
              if (beta <= alpha) return valor;
          }
      }
      return valor;
  }
  private int heuristica_c4(Tauler estat, int player){
      
  }
  
}