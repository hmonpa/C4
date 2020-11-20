package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;
        
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
    /*int col = (int)(8.0D * Math.random());
    while (!t.movpossible(col)) {
      col = (int)(8.0D * Math.random());
    }
    return col;*/
  }
  
  public String nom()
  {
    return nom;
  }
  // player es el color
  private int max(Tauler estat, int depth, int player, int alpha, int beta){
      // Max
      if (Terminal(estat) || depth == 0){
          // return heuri(estat, p1)
      }
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);        // player pendiente de asegurar implementación
              if (estat2.solucio(i, player)){   // player pendiente de asegurar implementación
                  return Integer.MAX_VALUE;
              }
              int valor = min(estat2, depth-1, player, alpha, beta);    // pdte implementar min
              alpha = Math.max(alpha, valor);
              if (beta <= alpha) return alpha;
          }
      }
      return alpha;
  }
  
    // player es el color
  private int min(Tauler estat, int depth, int player, int alpha, int beta){
      // Max
      if (Terminal(estat) || depth == 0){
          // return heuri(estat, p1)
      }
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);        // player pendiente de asegurar implementación
              if (estat2.solucio(i, player)){   // player pendiente de asegurar implementación
                  return Integer.MIN_VALUE;
              }
              int valor = max(estat2, depth-1, player, alpha, beta);    // pdte implementar min
              alpha = Math.max(alpha, valor);
              if (beta <= alpha) return alpha;
          }
      }
      return alpha;
  }
     
  private boolean Terminal(Tauler t){
      //return !t.espotmoure();
      //return t.
  }
  
  /* int minimax(int depth, int nodeIndex, 
            bool maximizingPlayer, 
            int values[], int alpha,
            int beta) 
{ 

    // Terminating condition. i.e
    // leaf node is reached 
    if (depth == 3) 
        return values[nodeIndex]; 

    if (maximizingPlayer) 
    { 
        int best = MIN; 

        // Recur for left and
        // right children 
        for (int i = 0; i < 2; i++) 
        { 

            int val = minimax(depth + 1, nodeIndex * 2 + i,
                              false, values, alpha, beta); 
            best = max(best, val); 
            alpha = max(alpha, best); 

            // Alpha Beta Pruning 
            if (beta <= alpha) 
                break; 
        } 
        return best; 
    } 
    else
    { 
        int best = MAX; 

        // Recur for left and 
        // right children 
        for (int i = 0; i < 2; i++) 
        { 
            int val = minimax(depth + 1, nodeIndex * 2 + i, 
                              true, values, alpha, beta); 
            best = min(best, val); 
            beta = min(beta, best); 

            // Alpha Beta Pruning 
            if (beta <= alpha) 
                break; 
        } 
        return best; 
    } 
}*/

    private int quinJugadorSoc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}