package edu.epsevg.prop.lab.c4;

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
    int col = (int)(8.0D * Math.random());
    while (!t.movpossible(col)) {
      col = (int)(8.0D * Math.random());
    }
    return col;
  }
  
  public String nom()
  {
    return nom;
  }
}