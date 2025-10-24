package botTelegram.clients;

import botTelegram.dtos.Fuente.HechoDTO;
import botTelegram.dtos.Fuente.PdiDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FuenteRetrofitClient {

    @POST("/hecho")
    Call<HechoDTO> crearHecho(@Body HechoDTO hecho);

    @POST("/pdis")
    Call<PdiDTO> crearPdi(@Body PdiDTO pdi);

    @GET("/hecho/{id}")
    Call<HechoDTO> getHecho(@Path("id") String id);
}
