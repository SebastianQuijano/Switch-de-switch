
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brownie
 */
public class SwitchDeSwitch {

    String array[];
    int acabo;
    ArrayList<ArrayList> arreglo = new ArrayList();
    StringTokenizer st;
    boolean tengoelarreglo = false;
    String archivo;
    boolean llenearreglos = false;
    int referencia, pregunta;
    int posicion = 0;
    String cabecera = "import java.util.Scanner;\n"
            + "\n"
            + "public class" + archivo + "{\n"
            + "    \n"
            + "    \n"
            + "    public static void main(String[] args) {\n"
            + "     Scanner sc = new Scanner(System.in);";
    String parte2 = "";
    String parte1 = "";

    public void LeerArchivo(String archivotxt) {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter w = null;
        BufferedWriter bw = null;
        PrintWriter wr = null;
        StringTokenizer starc;
        String cabecera = "";

        File f;
        File f2;
        starc = new StringTokenizer(archivotxt, ".");
        String p = starc.nextToken();
        f = new File(p + ".java");
        cabecera = "import java.util.Scanner;\n"
                + "\n"
                + "public class " + p + "{\n"
                + "    \n"
                + "    \n"
                + "    public static void main(String[] args) {\n"
                + "     Scanner sc = new Scanner(System.in);";

        try {

            w = new FileWriter(f);
            bw = new BufferedWriter(w);
            wr = new PrintWriter(bw);
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(archivotxt);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            
            while ((linea = br.readLine()) != null) {
//                acabo = br.read();
                if (!tengoelarreglo) {//creo el arreglo de arreglos tomando el primer numero como referencia
                    CrearArreglos(linea);
                } else if (posicion != referencia) {//lleno los arreglos con sus respectivos valores
                    LlenarArreglos(linea);
                } else if (tengoelarreglo == true && posicion == referencia) {//empiezo a crear el codigo
                    parte2 = parte2 + (CrearCodigo(linea));

                }
            }
            //Escribe en el archivo, al final sin borrar lo anterior
            wr.append(cabecera + "\n" + parte1 + parte2 + "}\n}");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    wr.close();
                    bw.close();
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearArreglos(String line) {
        int a = Integer.parseInt(line);//tomo el entero que me dice cuantos arreglos metere en el arreglo
        referencia = a;//variable que uso para impresion de variables y opciones
        for (int i = 0; i < a; i++) {//meto arreglos en el arreglo
            arreglo.add(new ArrayList<>());

        }
        tengoelarreglo = true;
    }

    public void LlenarArreglos(String line) {

        String variable = "";
        String opciones = "";
        st = new StringTokenizer(line, "\t");
        int a = Integer.parseInt(st.nextToken());
        int aux = a - 1;
        for (int i = 0; i < a; i++) {//analizo la linea de valores

            arreglo.get(posicion).add(i, st.nextToken());//agrego en el arreglo posicion el valor next token que separa por tabulaciones
            if (i == 0 && posicion < referencia - 1) {
                //tomo el nombre de la variable
                parte1 = parte1 + "\n\tSystem.out.println(\"Ingrese el valor de: " + arreglo.get(posicion).get(i) + "\");";
                //creo la variable con el mismo nombre de tipo entero para guardar el valor del usuario
                variable = "\n\tint " + arreglo.get(posicion).get(i) + " = sc.nextInt();\n";
            }
        }
        if (posicion != referencia - 1) {//ciclo para mostrar opciones a escoger
            for (int j = 1; j < arreglo.get(posicion).size(); j++) {
                int opcion = j - 1;
                opciones = opciones + " " + arreglo.get(posicion).get(j) + " :" + opcion; //voy agregando cada opcion a un string para luego imprimirlo
            }
        }
        parte1 = parte1 + "\nSystem.out.println( \"" + opciones + "\");" + variable;//meto las opciones a escoger con la variable a donde asignare
        posicion++;//aumento posicion para saber en que arreglo me encuentro
        if (posicion == referencia) {// si se cumple ya termine
            llenearreglos = true;//indicador
        }
    }

    public String CrearCodigo(String line) {
        String escribir = "";
        String tabulaciones = "";
        String anadir;
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {//si es un numero
                if (line.charAt(i + 1) == 'p') {//si hay p significa que es pregunta
                    //meterle el switch(a){
                    int aux = Integer.parseInt(line.charAt(i) + "");
                    escribir = tabulaciones + escribir + "switch (" + arreglo.get(aux).get(0) + ") {\n";//uso como referencia el nombre del atributo
                    i += 2;//muevo mi puntero
                } else if (line.charAt(i + 1) == '*') {//si hay asterisco es clase
                    //meter este string, pues es la repsuesta
                    int aux = Integer.parseInt(line.charAt(i + 2) + "");
                    escribir = tabulaciones + escribir + "System.out.println(\"" + arreglo.get(referencia - 1).get(aux) + "\");" + tabulaciones + "\nbreak;\n";
                    i++;
                } else if (line.charAt(i + 1) == '.') {//si hay punto es un case
                    escribir = tabulaciones + escribir + "case " + line.charAt(i + 2) + ":\n";
                    i += 2;
                }
            } else if (line.charAt(i) == '*') {//es respuesta
                int aux = Integer.parseInt(line.charAt(i + 1) + "");
                escribir = tabulaciones + escribir + "System.out.println(\"" + arreglo.get(referencia - 1).get(aux) + "\");\n" + tabulaciones + "break;\n";
                i++;
            } else if (line.charAt(i) == '}') {
//                if (br.read()!=-1) {
                    escribir = escribir + "}\nbreak;\n";
//                }
                
            } else if (line.charAt(i) == '\t') {
                tabulaciones = tabulaciones + "\t";
            }
        }
        return escribir;
    }

    public void Escribir(String anadir, String archivo) {//Este no lo uso, solo tome el codigoprestado para usarlo en otro lado
        File f;
        f = new File(archivo);
        //Escritura
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            //wr.write("Esta es una linea de codigo");//escribimos en el archivo
            wr.append(anadir); //concatenamos en el archivo sin borrar lo existente
            //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
            //de no hacerlo no se escribirá nada en el archivo
            wr.close();
            bw.close();
        } catch (IOException e) {
        };
    }

    public static void main(String[] args) {

        SwitchDeSwitch a = new SwitchDeSwitch();
        Scanner sc = new Scanner(System.in);
        String archivo;
        if (args.length != 0) {
            archivo = args[0];
        } else {
            System.out.println("Ingrese el nombre del archivo");
            archivo = sc.next();
        }
        a.LeerArchivo(archivo);
    }
}
