package botTelegram.clients;

import botTelegram.dtos.solicitud.SolicitudDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Query;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class BusquedaProxy {
    private String endpoint;
    private BusquedaRetrofitClient service;

    public BusquedaProxy (ObjectMapper mapper){
        var env = System.getenv();
        this.endpoint = env.getOrDefault("URL_BUSQUEDA", "https://busquedaservice.onrender.com");

        var retrofit = new Retrofit.Builder().
                        baseUrl(endpoint).
                        addConverterFactory(JacksonConverterFactory.create(mapper))
                        .build();
        this.service = retrofit.create(BusquedaRetrofitClient.class);
    }

    @SneakyThrows
    public Map<String, Object> search(String q, String tag, int page, int size){
        Response<Map<String, Object>> response = service.search(q,tag,page,size).execute();

        if (!response.isSuccessful()) {
            throw new RuntimeException("Error conectándose");
        }

        Map<String, Object> hechosYpdis = response.body();
        if(hechosYpdis == null){
            throw new NoSuchElementException("No existen hechos o pdis con esa palabra clave ");
        }
        return hechosYpdis;
    }

}
