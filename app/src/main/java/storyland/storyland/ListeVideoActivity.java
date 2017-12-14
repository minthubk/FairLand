package storyland.storyland;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.lang.String;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.Toast;
import android.widget.VideoView;


public class ListeVideoActivity extends BaseActivity {


    TableLayout table;
    TableRow row; // création d'un élément : ligne
    TextView tv1,tv2,tv3; // création des cellules

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        String[] listeVideo;
        //recupere tous les fichiers contenant un certain noms

        File directory = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES), "StoryLand");

        // Create the storage directory if it does not exist
        if (! directory.exists()){
            if (! directory.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return;
            }
        }

        File[] files = directory.listFiles();
        table = (TableLayout) findViewById(R.id.idTable);
        List<TableRow> tableRows = new ArrayList<>();
        // on prend le tableau défini dans le layout
        if(files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                row = new TableRow(this); // création d'une nouvelle ligne
                row.setMinimumHeight(100);
                row.setGravity(Gravity.CENTER_VERTICAL);

                tv1 = new TextView(this);
                String value = files[i].getName();// création cellule
                tv1.setText(value);
                tv1.setId(100+i);// ajout du texte
                tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
                // adaptation de la largeur de colonne à l'écran :
                tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 3));

                // idem 2ème cellule
                tv2 = new TextView(this);
                tv2.setText("Partager");
                tv2.setId(200+i);
                tv2.setGravity(Gravity.RIGHT);
                tv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                // idem 3ème cellule
                tv3 = new TextView(this);
                tv3.setText("Supprimer");
                tv3.setId(300+i);
                tv3.setGravity(Gravity.RIGHT);
                tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                // ajout des cellules à la ligne
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);
                row.setTag(i);
                tv1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        // It's index
                        String text = ((TextView)((TableRow)view.getParent()).getChildAt(0)).getText().toString();
                        Intent intent = new Intent(ListeVideoActivity.this, AndroidVideoPlayer.class);
                        Bundle b = new Bundle();
                        b.putString("video", Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+"/StoryLand/"+text); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                    }
                });






                        /*Log.d("id",value);
                        String text = ((TextView) row.getChildAt(0)).getText().toString();
                        TextView textView = (TextView)(((TableRow)row)).getChildAt(0);
                        Log.d("video", text);
                        Intent intent = new Intent(ListeVideoActivity.this, AndroidVideoPlayer.class);
                        Bundle b = new Bundle();
                        b.putString("video", Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+"/StoryLand/"+text); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        startActivity(intent);
                    }
                });*/

                // ajout de la ligne au tableau
                tableRows.add(row);
            }
            for (int y = 0 ; y < tableRows.size(); y++){
                table.addView(tableRows.get(y));
            }
        }else{
            row = new TableRow(this); // création d'une nouvelle ligne
            tv1 = new TextView(this); // création cellule
            tv1.setText("Aucune Video");
        }
    }
}
