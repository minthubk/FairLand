package storyland.storyland;

/**
 * Created by thibault on 13/12/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

// classe qui rajoute le menu en haut à droite
public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onBackPressed() {
        Intent homeIntent = new Intent(this, MenuActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.prendre_video:
                Intent priseVideoIntent = new Intent(BaseActivity.this, CameraActivity.class);
                startActivity(priseVideoIntent);
                finish();
                return true;
            case R.id.mes_videos:
                Intent listeVideosIntent = new Intent(BaseActivity.this, ListeVideoActivity.class);
                startActivity(listeVideosIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
