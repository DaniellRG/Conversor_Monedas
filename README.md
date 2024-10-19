# Conversor de Monedas

Este proyecto es un Conversor de Monedas desarrollado en Java como parte del desafío del programa ONE de Oracle y Alura Latam.

## Descripción

El Conversor de Monedas es una aplicación de consola que permite a los usuarios convertir entre diferentes monedas utilizando tasas de cambio en tiempo real obtenidas de una API externa.

## Características

- Conversión entre múltiples monedas
- Uso de tasas de cambio en tiempo real
- Historial de conversiones
- Interfaz de usuario por consola

## Requisitos previos

- Java JDK 11 o superior
- Maven (para gestión de dependencias)

## Instalación

1. Clona este repositorio:
   ```
   git clone https://github.com/tu-usuario/ConversorMonedas.git
   ```

2. Navega al directorio del proyecto:
   ```
   cd ConversorMonedas
   ```

3. Compila el proyecto con Maven:
   ```
   mvn clean install
   ```

## Uso

Para ejecutar el programa, usa el siguiente comando desde el directorio del proyecto:

```
java -jar target/ConversorMonedas-1.0-SNAPSHOT.jar
```

Sigue las instrucciones en pantalla para realizar conversiones de moneda.

## Estructura del proyecto

- `src/main/java/com/tuempresa/conversormonedas/`: Contiene los archivos fuente Java
  - `Main.java`: Punto de entrada de la aplicación
  - `ConversorMonedas.java`: Clase principal que maneja la lógica de conversión

## Dependencias

- [Gson](https://github.com/google/gson): Para el manejo de JSON
- [Java HTTP Client](https://openjdk.java.net/groups/net/httpclient/intro.html): Para realizar peticiones HTTP a la API de tasas de cambio

## Demostración

![Demostración](assets/Conversordemonedas.gif)


