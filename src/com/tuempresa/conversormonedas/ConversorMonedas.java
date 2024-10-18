package com.tuempresa.conversormonedas;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ConversorMonedas {
    private static final String API_KEY = "e86e7162ebf7e663e8a5b8e4";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private final Gson gson;
    private final HttpClient httpClient;
    private Map<String, Double> tasasDeCambio;
    private List<String> historialConversiones;
    private static final int MAX_HISTORIAL = 10;

    public ConversorMonedas() {
        this.gson = new Gson();
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.tasasDeCambio = new HashMap<>();
        this.historialConversiones = new ArrayList<>();
    }

    public void actualizarTasasDeCambio(String monedaBase) throws Exception {
        HttpResponse<String> response = obtenerTasasDeCambio(monedaBase);
        if (response.statusCode() == 200) {
            String jsonResponse = response.body();
            tasasDeCambio = obtenerTodasLasMonedas(jsonResponse);
        } else {
            throw new Exception("Error al obtener tasas de cambio. Código de estado: " + response.statusCode());
        }
    }

    public double convertir(String monedaOrigen, String monedaDestino, double cantidad) throws Exception {
        if (!tasasDeCambio.containsKey(monedaOrigen) || !tasasDeCambio.containsKey(monedaDestino)) {
            throw new Exception("Moneda no soportada");
        }

        double tasaOrigen = tasasDeCambio.get(monedaOrigen);
        double tasaDestino = tasasDeCambio.get(monedaDestino);

        double resultado = calcularConversion(cantidad, tasaOrigen, tasaDestino);
        registrarConversion(monedaOrigen, monedaDestino, cantidad, resultado);
        return resultado;
    }

    private double calcularConversion(double cantidad, double tasaOrigen, double tasaDestino) {
        return (cantidad / tasaOrigen) * tasaDestino;
    }

    private HttpResponse<String> obtenerTasasDeCambio(String monedaBase) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + monedaBase))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("User-Agent", "ConversorMonedas/1.0")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private Map<String, Double> obtenerTodasLasMonedas(String jsonResponse) {
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject tasas = jsonObject.getAsJsonObject("conversion_rates");

        Map<String, Double> todasLasMonedas = new HashMap<>();
        for (String moneda : tasas.keySet()) {
            todasLasMonedas.put(moneda, tasas.get(moneda).getAsDouble());
        }
        return todasLasMonedas;
    }

    public void mostrarTasasDeCambio() {
        System.out.println("Tasas de cambio actuales:");
        tasasDeCambio.forEach((moneda, tasa) -> System.out.printf("%s: %.4f%n", moneda, tasa));
    }

    public List<String> obtenerMonedasDisponibles() {
        return new ArrayList<>(tasasDeCambio.keySet());
    }

    private void registrarConversion(String monedaOrigen, String monedaDestino, double cantidad, double resultado) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String registro = String.format("[%s] %.2f %s = %.2f %s",
                ahora.format(formatter), cantidad, monedaOrigen, resultado, monedaDestino);

        historialConversiones.add(0, registro);
        if (historialConversiones.size() > MAX_HISTORIAL) {
            historialConversiones.remove(historialConversiones.size() - 1);
        }
    }

    public List<String> obtenerHistorialConversiones() {
        return new ArrayList<>(historialConversiones);
    }
}