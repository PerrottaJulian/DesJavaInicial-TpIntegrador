import java.util.ArrayList;

public class Ronda {
    public Ronda(int numero, Partido partido1, Partido partido2){
        this.numero = numero;
        this.partidos = new Partido[]{partido1,partido2};
    }
    private int numero;
    public Partido[] partidos;
    public int puntos = 0;


    public int getNumero() {
        return numero;
    }
    public Partido[] getPartidos() {
        return partidos;
    }
    public void SumarPuntos(int puntos){
        this.puntos += puntos;
    }

}
