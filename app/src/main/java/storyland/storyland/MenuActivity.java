package storyland.storyland;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Menu");

        addClickListeners();

        // code censé faire fonctionner le bouton back
/*        // Intent for the activity to open when user selects the notification
        Intent detailsIntent = new Intent(this, DetailsActivity.class);

// Use TaskStackBuilder to build the back stack and get the PendingIntent
        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        // add all of DetailsActivity's parents to the stack,
                        // followed by DetailsActivity itself
                        .addNextIntentWithParentStack(upIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent);*/
    }

    public void addClickListeners() {

        // click listeners
        findViewById(R.id.prendre_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(MenuActivity.this, CameraActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        findViewById(R.id.mes_videos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(MenuActivity.this, ListeVideoActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}
