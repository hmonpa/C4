package edu.epsevg.prop.lab.c4;
import edu.epsevg.prop.lab.c4.Juga2;
import java.util.ArrayList;
        
/**
 * @author Héctor Montesinos
 * @author Alberto López Rey
 */

public class LaRaya implements Jugador, IAuto {
  private String nom;
  private int depth;
  
  public LaRaya()
  {
    nom = "La Raya";
    depth = 8;
  }
  
  public int moviment(Tauler t, int color)
  {
      //return inici_minmax(t,8,color);
      return inici_minmax(t,color);
  }
  
  public String nom()
  {
    return nom;
  }
  
  public LaRaya(int depth){
      this.nom = "La Raya";
      this.depth = depth;
  }
  
//  private int inici_minmax(Tauler estat,int depth, int player){
    private int inici_minmax(Tauler estat,int player){
      //int LNfirst[] = new int[estat.getMida()];
      int valor, fila = 0;
      int max = Integer.MIN_VALUE;
      for(int i = 0; i < estat.getMida(); ++i){
         Tauler estat2 = new Tauler(estat);
         if(estat.movpossible(i)){
             estat2.afegeix(i, player); 
             //LNfirst[i] = min(estat2, depth-1, player, 0, 0, estat2.solucio(i, player));
             valor = min(estat2, depth, player, Integer.MAX_VALUE, Integer.MIN_VALUE);
             System.out.println("Columna " + i + " valor = " + valor);
               if (valor > max){
                    //max = LNfirst[i];
                    max = valor;
                    fila = i;
                }
         }
      }
      
      //int max = LNfirst[1];
      //int fila = 0;
      /*for(int i = 1; i < estat.getMida(); ++i){
          //if (max < LNfirst[i]){
          if (valor > max){
               //max = LNfirst[i];
               max = valor;
               fila = i;
          }
      }*/
      //System.out.println(fila);
      return fila;
  }
  // player es el color
  //private int max(Tauler estat, int depth, int player, int alpha, int beta, boolean solucio){
  private int max(Tauler estat, int depth, int player, int alpha, int beta){
      // Max
      //if (solucio || depth == 0){
      if (!estat.espotmoure() || depth == 0){
          //return heuristica(estat, player);
          alpha = this.heuristica(estat, player);
      }
      else{
      //int valor = -1000000000;
      //solucio = false;
      //if(depth==7)System.out.println("Max >"+depth);
      for (int i=0;i<estat.getMida();i++){    
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);        // player pendiente de asegurar implementación
              if (estat2.solucio(i, player)){   // player pendiente de asegurar implementación
                  //solucio = true;
                  return Integer.MAX_VALUE;
              }
              //valor = min(estat2, depth-1, player, alpha, beta, solucio);    // pdte implementar min
              int valor = min(estat2, depth-1, player, alpha, beta);    // pdte implementar min
              //System.out.println("MAX: Columna " + i + " valor = " + valor);
              alpha = Math.max(alpha, valor);
              //if (beta <= alpha) return valor;
              if (beta <= alpha) return alpha;
          }
      }
      }
      //return valor;
      return alpha;
  }
  
    // player es el color
  private int min(Tauler estat, int depth, int player, int alpha, int beta){
      // Min
      //if (solucio || depth == 0){
      if (!estat.espotmoure() || depth == 0){
          //return heuristica(estat, player);
          beta = heuristica(estat, player);
      }
      else{
      //int valor = 1000000000;
      //solucio = false;
      //if(depth==7)System.out.println("Min >"+depth);
      for (int i=0;i<estat.getMida();i++){
          if (estat.movpossible(i)){
              Tauler estat2 = new Tauler(estat);
              estat2.afegeix(i, player);        
              if (estat2.solucio(i, player)){  
                  //solucio = true;
                  return Integer.MIN_VALUE;
              }
              //valor = max(estat2, depth-1, player, alpha, beta,solucio);    // pdte implementar min
              int valor = max(estat2, depth-1, player, alpha, beta); 
              //System.out.println("MIN: " + i + " valor = " + valor);
              beta = Math.min(beta, valor);
              //if (beta <= alpha) return valor;
              if (beta <= alpha) return beta;
          }
      }
      }
      //return valor;
      return beta;
  }
  
    private int heuristica (Tauler t, int color){
    float heu = 0.0f;
    int num=0;
    for (int i=0;i<t.getMida();i++){
      //LLAMAR A LA VERTICAL Y USAR LA FILA COMO SI FUERA UNA COLUMNA
        //heu=HeuristicaRecorregutVertical(t, fila,color,heu);
        for (int col = 0; col <t.getMida(); ++col) {
          //Per cada posició ens interessa mirar totes les possiblitats, amunt, avall, dreta, esquerra i les 4 diagonals
          num=Recorregut(t, i, col, t.getColor(i, col), "DiagonalEsquerreAvall");
          heu=calculaheuristica(t, heu, num, i, col, color);
          num=Recorregut(t, i, col, t.getColor(i, col), "DiagonalEsquerreAmunt");
          heu=calculaheuristica(t, heu, num, i, col, color);
          num=Recorregut(t, i, col, t.getColor(i, col), "DiagonalDretaAvall");
          heu=calculaheuristica(t, heu, num, i, col, color);
          num=Recorregut(t, i, col, t.getColor(i, col), "DiagonalDretaAmunt");
          heu=calculaheuristica(t, heu, num, i, col, color);
          num=Recorregut(t, i, col, t.getColor(i, col), "HoritzonalEsquerra");
          heu=calculaheuristica(t, heu, num, i, col, color);
          num=Recorregut(t, i, col, t.getColor(i, col), "HoritzonalDreta");
          heu=calculaheuristica(t, heu, num, i, col, color);
        }
    }
    return (int)heu;
  }

  private float calculaheuristica (Tauler t, float heuactual, int quantesnostres, int fila, int col, int color){
    //Si el color es el nostre serà 1 pel que serà positiu i se sumarà a la heuristicaactua
    //Si el color es el de l'enemic serà -1 i per tant al ser un número negatiu es restarà a la heuristica
    //Formula=2nostres+20*3nostres+1000*4nostres - (2seves+20*3seves+1000*4seves)
    if (quantesnostres == 2) {
        heuactual = heuactual + 10 * t.getColor(fila, col) * color;
    }
    else if (quantesnostres == 3) {
        heuactual = heuactual + 500 * t.getColor(fila, col) * color;
    }
    else if (quantesnostres >= 4) {
        heuactual = heuactual + 100000 * t.getColor(fila, col) * color;
    }
    return heuactual;
  }

  private int Recorregut(Tauler t, int f, int c, int color, String TipusMoviment) {
      int colmax= (int)(t.getMida());
      int nostres = 0;
      int contador = 0;
      int col = c;
      int fila = f;
      boolean target = false;
      String DiagonalEsquerreAvall ="DiagonalEsquerreAvall";
      String DiagonalEsquerreAmunt ="DiagonalEsquerreAmunt";
      String DiagonalDretaAvall ="DiagonalDretaAvall";
      String DiagonalDretaAmunt ="DiagonalDretaAmunt";
      String HoritzonalEsquerra ="HoritzonalEsquerra";
      String HoritzonalDreta ="HoritzonalDreta";
      //Comprobar que la posició que volem mirar està dintre del tauler, forma part de les 4 que volem mirar i no ha incomplert cap restriccio
      //Restriccions: només pot haver-hi un mateix color o buits, si està buit la de sota no pot estar buida
      while (col >= 0 && col < colmax && fila >= 0 && fila < colmax && !target && contador < 4) {
          int coloract=t.getColor(fila, col);
          if ((coloract != color) && (coloract!=0)) {//si  donde estamos es del rival, nos salimos
              target = true;
          }
          else if (fila > 0 && coloract==0 && t.getColor(fila - 1, col) == 0) {//mira si hay alguna ficha debajo, es para las diagonales y horizontales, si no hay nada salimos
              target = true;
          }
          else if (coloract == color) {
              ++nostres;
          }
          ++contador;
          if (DiagonalEsquerreAvall.equals(TipusMoviment)){
            col=col-1;
            fila=fila-1;
          }
          else if (DiagonalEsquerreAmunt.equals(TipusMoviment)){
            col=col-1;
            fila=fila+1;
          }
          else if (DiagonalDretaAvall.equals(TipusMoviment)){
            col=col+1;
            fila=fila-1;
          }
          else if (DiagonalDretaAmunt.equals(TipusMoviment)){
            col=col+1;
            fila=fila+1;
          }
          else if (HoritzonalEsquerra.equals(TipusMoviment)){
            col=col-1;
          }
          else if (HoritzonalDreta.equals(TipusMoviment)){
            col=col+1;
          }
          else{
            //dades incorrectes
            target=true;
            break;
          }
      }
      if (target==true && contador!=4) {
        //No s'ha complert una restriccio o ha quedat fora del tauler
          nostres = 0;
      }
      return nostres;
  }

  private float HeuristicaRecorregutVertical(Tauler t, int c, int color, float heur){
    int coloract=0; //color de la primera posició
    int seguides=0; //quantes seguides tenim?
    int fil=0; //fila per saber el color que mirem
    for (int fila=t.getMida()-1; fila>=0; --fila){
      int colorpos=t.getColor(fila, c);
      if(t.getColor(fila, c)!=0){ //si no es 0 començem a comptar
        if(seguides==0){  //inicialitzem valors
          coloract=colorpos;
          fil=fila;   //posteriorment per la heuristica
          ++seguides;
        }
        else if (colorpos!=coloract){
          heur=calculaheuristica(t, heur, seguides, fil, c, color);
          return heur;
        }
        else{
          ++seguides;
        }
      }
      if(seguides>=4){
        heur=calculaheuristica(t, heur, seguides, fil, c, color);

        return heur;
      }
    }
    return heur;
  }
}