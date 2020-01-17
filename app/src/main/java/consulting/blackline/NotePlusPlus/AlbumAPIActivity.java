package consulting.blackline.NotePlusPlus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

public class AlbumAPIActivity extends AppCompatActivity {


    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_api);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAlbumAPI interfaceAlbumAPI = retrofit.create(InterfaceAlbumAPI.class);

        Call<List<AlbumItemModel>> call = interfaceAlbumAPI.getAlbums();

        call.enqueue(new Callback<List<AlbumItemModel>>() {
            @Override
            public void onResponse(Call<List<AlbumItemModel>> call, Response<List<AlbumItemModel>> response) {

                List<AlbumItemModel> albums = response.body();

                for (AlbumItemModel album : albums) {

                    mImageUrls.add(album.getThumbnailUrl());
                    mNames.add(album.getTitle());

                }
                initRecyclerView();
            }
            @Override
            public void onFailure(Call<List<AlbumItemModel>> call, Throwable t) {

            }
        });
    }


    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AlbumAPIRecyclerViewAdapter adapter = new AlbumAPIRecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
