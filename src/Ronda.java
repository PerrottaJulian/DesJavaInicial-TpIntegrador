import java.util.ArrayList;

public class Ronda {
    public Ronda(int numero){
        this.numero = numero;
    }
    private int numero;
    public ArrayList<Partido> partidos = new ArrayList<Partido>();
    public int puntos = 0;


    public int getNumero() {
        return numero;
    }
    public void SumarPuntos(int puntos){
        this.puntos += puntos;
    }

}
