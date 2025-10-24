package botTelegram.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import botTelegram.dtos.Fuente.PdiDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class PdiProxy {

    private final String endpoint;
    private final PdiRetrofitClient service;

    public PdiProxy(ObjectMapper objectMapper) {
        var env = System.getenv();

        this.endpoint = env.getOrDefault("URL_PDI",
                "https://tpdds2025-procesadorpdi-2.onrender.com");

        var retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.service = retrofit.create(PdiRetrofitClient.class);
    }

    @SneakyThrows
    public List<botTelegram.dtos.Fuente.PdiDTO> getPdisPorHecho(String hechoId) throws IOException {
        Response<List<PdiDTO>> response = service.getPdisPorHecho(hechoId).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose con el componente Procesador PDI");
        }

        List<PdiDTO> pdis = response.body();
        if (pdis == null) {
            throw new IOException("Error: respuesta vacía al obtener PDIs por hecho");
        }
        return pdis;
    }
}

