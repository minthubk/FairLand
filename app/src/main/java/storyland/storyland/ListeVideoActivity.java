package storyland.storyland;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.lang.String;
import java.io.File;

import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;


public class ListeVideoActivity extends AppCompatActivity {


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
        table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        if(files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                row = new TableRow(this); // création d'une nouvelle ligne

                tv1 = new TextView(this); // création cellule
                tv1.setText(files[i].getName()); // ajout du texte
                tv1.setGravity(Gravity.LEFT); // centrage dans la cellule
                // adaptation de la largeur de colonne à l'écran :
                tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                // idem 2ème cellule
                tv2 = new TextView(this);
                tv2.setText("Partager");
                tv2.setGravity(Gravity.RIGHT);
                tv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                // idem 3ème cellule
                tv3 = new TextView(this);
                tv3.setText("Supprimer");
                tv3.setGravity(Gravity.RIGHT);
                tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                // ajout des cellules à la ligne
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);

                // ajout de la ligne au tableau
                table.addView(row);
            }
        }else{
            /*row = new TableRow(this); // création d'une nouvelle ligne
            tv1 = new TextView(this); // création cellule
            tv1.setText("Aucune Video");
            tv2 = new TextView(this); // création cellule
            tv3 = new TextView(this); // création cellule*/
        }
    }
}
