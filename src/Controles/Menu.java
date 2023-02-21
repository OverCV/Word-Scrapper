package Controles;

import Modelos.Explorador;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static Scanner ios = new Scanner(System.in);

    public static void inicio() {
        Map<Integer, Runnable> funciones = new HashMap<>();
        funciones.put(1, () -> {
            try {
                contarPalabra();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        String vistas = "1. Contar palabra en directorio. | 2. Otras opciones..." +
                "\n0. Salir.";

        int opcion = -1;
        while (true) {
            System.out.println(vistas + "\nIngrese un número para seleccionar una función: ");
            opcion = ios.nextInt();
            System.out.println();

            if (opcion == 0) {
                System.out.println("Gracias por usar nuestro programa.");
                return;
            }

            Runnable funcion = funciones.get(opcion);
            if (funcion != null) {
                funcion.run();
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    public static void contarPalabra() throws FileNotFoundException {
        System.out.println("Ingresa la ruta del directorio: ");
        String ruta = ios.next();
        System.out.println("Ingresa la palabra a buscar: ");
        String palabra = ios.next();

        System.out.println();

        Explorador explorer = new Explorador(ruta, palabra);
        explorer.leerArchivos();
    }
}
