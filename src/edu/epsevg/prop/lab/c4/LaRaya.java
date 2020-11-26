package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;
        
/**
 * @author hector, alberto
 */

public class LaRaya implements Jugador, IAuto {
  private String nom;
  private int depth;
  
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
      //int LNfirst[] = new int[estat.getMida()];
      int valor, fila = 0;
      int max = Integer.MIN_VALUE;
      for(int i = 0; i < estat.getMida(); ++i){
         if(estat.movpossible(i)){
             Tauler estat2 = new Tauler(estat);
             estat2.afegeix(i, player); 
             valor = min(estat2, depth-1, player, Integer.MAX_VALUE, Integer.MIN_VALUE, estat2.solucio(i, player));
              if (max < valor){
               max = valor;
               fila = i;
              }
         }
      }
      
      /*int max = LNfirst[0];
      int fila = 0;
      for(int i = 1; i < estat.getMida(); ++i){
          System.out.println("------> "+LNfirst[i]);
         
      }*/
      return fila;
  }
  // player es el color
  private int max(Tauler estat, int depth, int player, int alpha, int beta, boolean solucio){
      // Max
      if (solucio || depth == 0){
          return heuristica(estat, player);
      }
      int valor = Integer.MIN_VALUE;
      solucio = false;
      //if(depth==7)System.out.println("Max >"+depth);
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
      // Min
      if (solucio){
          return Integer.MAX_VALUE;
      }if(depth == 0){
          return heuristica(estat, player);
      }
      int valor = Integer.MAX_VALUE;
      solucio = false;
      //if(depth==7)System.out.println("Min >"+depth);
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
  
  private int heuristica (Tauler t, int color){
      
  }
    
