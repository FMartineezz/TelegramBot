package clients;
import dtos.Fuente.PdiDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface PdiRetrofitClient {
    @GET("/pdis")
    Call<List<PdiDTO>> getPdisPorHecho(@Query("hecho") String hechoId);
}
