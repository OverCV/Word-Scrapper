package Modelos;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Explorador {

    // Atributos de la clase
    private final String palabra;
    private final String ruta;
    private int total;

    // Constructor
    public Explorador(String ruta, String palabra) {
        this.ruta = ruta;
        this.palabra = palabra;
        this.total = 0;
    }

    /**
     * Explora los archivos de un directorio y cuenta la cantidad de veces que aparece una palabra en ellos.
     * Imprime el resultado de la exploración en la consola.
     */
    public void leerArchivos() {
        List<String> archivos = new ArrayList<>();
        File dir = new File(this.ruta);
        String resultado = "Resultado esperado: ";

        // Verificamos si la carpeta existe y es un directorio
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println(resultado + "Mensaje indicando que no se encuentra la carpeta indicada.\n");
            return;
        }

        // Obtenemos los archivos de la carpeta y los leemos uno por uno
        File[] files = dir.listFiles();
        for (File file : files) {
            resultado = leerArchivo(file);
            archivos.add(resultado);
        }

        // Mostramos los resultados obtenidos
        System.out.println("\nPalabra buscada: \"" + this.palabra + '"');
        for (String archivo : archivos) {
            System.out.println(archivo);
        }
        System.out.println("Total: " + this.total + " veces\n");
        this.total = 0;
    }

    /**
     * Lee el contenido de un archivo y cuenta la cantidad de veces que aparece una palabra en él.
     *
     * @param file el archivo que se desea leer.
     * @return una cadena con el resultado de la lectura del archivo.
     */
    private String leerArchivo(@NotNull File file) {
        int cadaLinea = 0;

        // Verificamos si el archivo existe y es un archivo de texto válido
        if (!file.isFile() || !esFormatoValido(file.getName())) {
            return "Mensaje indicando que no se encontraron archivos de texto en la carpeta.";
        }

        // Leemos el archivo y contamos las ocurrencias de la palabra buscada en cada línea
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Archivo: " + file.getName() + " que tiene el siguiente texto internamente:");
            while (scanner.hasNextLine()) {
                String regEx = "[\\s,.!\\(\\)]+";
                String nuevaLinea = scanner.nextLine();
                String[] palabras = nuevaLinea.split(regEx);

                cadaLinea += conteo(palabras);
                System.out.println(nuevaLinea);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } finally {
            return "Archivo " + file.getName() + ' ' + cadaLinea + " veces";
        }
    }

    /**
     * Cuenta la cantidad de veces que aparece una palabra en un arreglo de palabras.
     * Actualiza la variable total con la cantidad de veces que aparece la palabra en el archivo completo.
     *
     * @param palabras el arreglo de palabras en el que se desea buscar la palabra.
     * @return la cantidad de veces que aparece la palabra en el arreglo de palabras.
     */
    private int conteo(String[] palabras) {
        int cadaPalabra = 0;
        for (String cadena : palabras) {
            if (this.palabra.equals(cadena)) {
                cadaPalabra++;
            }
        }
        total += cadaPalabra;
        return cadaPalabra;
    }

    /**
     * Verifica si el nombre del archivo tiene un formato válido.
     *
     * @param nombreArchivo el nombre del archivo a verificar.
     * @return true si el formato es válido, de lo contrario false.
     */
    private boolean esFormatoValido(String nombreArchivo) {
        return nombreArchivo.endsWith(".txt") || nombreArchivo.endsWith(".xml") ||
                nombreArchivo.endsWith(".json") || nombreArchivo.endsWith(".csv");
    }
}