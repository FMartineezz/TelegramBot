package botTelegram.clients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Map;

public interface BusquedaRetrofitClient {
    @GET("/search")
    Call <Map<String, Object>> search(@Query("q") String q, @Query("tag") String tag, @Query("page") int page, @Query("size") int size);
}
