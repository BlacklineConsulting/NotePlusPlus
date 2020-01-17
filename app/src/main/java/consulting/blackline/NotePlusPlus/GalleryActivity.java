package consulting.blackline.NotePlusPlus;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class GalleryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            setImage(imageUrl, imageName);
        }
    }


    private void setImage(String imageUrl, String imageName){

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

}