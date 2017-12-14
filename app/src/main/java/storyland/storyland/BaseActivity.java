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

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("baseactivity", "add menu");
        addMenu();
    }

    protected void addMenu() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View menu = inflater.inflate(R.layout.menu, null);

        // click listeners
        menu.findViewById(R.id.prendre_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(BaseActivity.this, CameraActivity.class);
                startActivity(homeIntent);
                finish();

                Log.v("tab", "click sur prendre vidéo");
            }
        });
        menu.findViewById(R.id.mes_videos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(BaseActivity.this, ListeVideoActivity.class);
                startActivity(homeIntent);
                finish();

                Log.v("tab", "click sur mes vidéos");
            }
        });
        menu.findViewById(R.id.edition_videos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(BaseActivity.this, EditionVideoActivity.class);
                startActivity(homeIntent);
                finish();

                Log.v("tab", "click sur edition vidéo");
            }
        });

        // insert into main view
        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.menu_insert);
        insertPoint.addView(menu, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TableLayout menu = findViewById(R.id.menu);
        int visibility = menu.getVisibility();

        if (visibility == View.INVISIBLE) {
            menu.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.INVISIBLE);
        }

        return true;
    }
}
