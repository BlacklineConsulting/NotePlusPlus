package consulting.blackline.NotePlusPlus;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceAlbumAPI {

    @GET("photos")
    Call<List<AlbumItemModel>> getAlbums();

}
