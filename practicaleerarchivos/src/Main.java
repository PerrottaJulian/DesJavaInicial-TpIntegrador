import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("resultados.csv");
        BufferedReader bf = new BufferedReader(new FileReader(path.toFile()));
        String linea = "";
        while ((linea = bf.readLine()) != null){
            String[] palabras = linea.split(" ");
            System.out.println(linea);
            }







    }
    public static void printArrayString(String[] array){
        for (int i = 0; i < array.length ; i++) {
            System.out.println(array[i]);

        }
    }

}