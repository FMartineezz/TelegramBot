package botTelegram.clients;

import java.util.List;

import botTelegram.dtos.Fuente.HechoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AgregadorRetrofitClient {
	@GET("/coleccion/{nombre}/hechos")
	Call<List<HechoDTO>> getHechosPorColleccion(@Path("nombre") String id);
}
