package com.tuempresa.conversormonedas;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConversorMonedas conversor = new ConversorMonedas();

    public static void main(String[] args) {
        try {
            System.out.println("Bienvenido al Conversor de Monedas");
            conversor.actualizarTasasDeCambio("USD");

            while (true) {
                mostrarMenu();
                int opcion = obtenerOpcionUsuario();

                switch (opcion) {
                    case 1:
                        realizarConversion();
                        break;
                    case 2:
                        mostrarTasasDeCambio();
                        break;
                    case 3:
                        mostrarMonedasDisponibles();
                        break;
                    case 4:
                        mostrarHistorialConversiones();
                        break;
                    case 5:
                        System.out.println("Gracias por usar el Conversor de Monedas. ¡Hasta luego!");
                        return;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }

                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú del Conversor de Monedas ---");
        System.out.println("1. Realizar una conversión");
        System.out.println("2. Ver tasas de cambio actuales");
        System.out.println("3. Ver monedas disponibles");
        System.out.println("4. Ver historial de conversiones");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcionUsuario() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void realizarConversion() {
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Ingrese la moneda de origen: ");
        String monedaOrigen = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese la moneda de destino: ");
        String monedaDestino = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese la cantidad a convertir: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        double cantidad = scanner.nextDouble();

        try {
            double resultado = conversor.convertir(monedaOrigen, monedaDestino, cantidad);
            System.out.printf("%.2f %s = %.2f %s%n", cantidad, monedaOrigen, resultado, monedaDestino);
        } catch (Exception e) {
            System.out.println("Error en la conversión: " + e.getMessage());
        }
    }

    private static void mostrarTasasDeCambio() {
        conversor.mostrarTasasDeCambio();
    }

    private static void mostrarMonedasDisponibles() {
        System.out.println("Monedas disponibles para conversión:");
        conversor.obtenerMonedasDisponibles().forEach(System.out::println);
    }

    private static void mostrarHistorialConversiones() {
        System.out.println("Historial de conversiones:");
        conversor.obtenerHistorialConversiones().forEach(System.out::println);
    }
}