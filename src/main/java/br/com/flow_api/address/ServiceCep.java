package br.com.flow_api.address;

import br.com.flow_api.external.Utils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceCep {

    static int successCode = 200;

    private ServiceCep() {
        throw new IllegalStateException("Utility class");
    }


    public static Address findByCep(String cep) throws Exception {
        String urlToCall = "http://viacep.com.br/ws/" + cep + "/json";

        try {
            URL url = new URL(urlToCall);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != successCode)
                throw new Exception("HTTP error code : " + connection.getResponseCode());

            BufferedReader response = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String jsonforString = Utils.converteJsonEmString(response);

            Gson gson = new Gson();
            Address address = gson.fromJson(jsonforString, Address.class);

            return address;
        } catch (Exception e) {
            throw new Exception("ERROR: " + e);
        }
    }
}
