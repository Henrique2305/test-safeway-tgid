package br.com.safeway.safeway.service;

import br.com.safeway.safeway.model.Tipo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TransactionAlert {

    public static void alert(Tipo tipo, String cliente, String cnpj, BigDecimal valor) {
        String webhookURL = "https://webhook.site/693c3bd7-bff9-4ba8-bf15-75c6971a17b8";

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            StringBuilder requestBody = new StringBuilder();
            requestBody.append("{");
            requestBody.append("\"tipo\":\"").append(tipo).append("\",");
            requestBody.append("\"cliente\":\"").append(cliente).append("\",");
            requestBody.append("\"CNPJ\":\"").append(cnpj).append("\",");
            requestBody.append("\"valor\":").append(valor);
            requestBody.append("}");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(webhookURL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            requestBody.toString(),
                            StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();
            System.out.println("Transação enviada!");
            System.out.println("Código de resposta: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
