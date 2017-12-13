package storyland.storyland;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.lang.String;
import java.io.File;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ListeVideoActivity extends AppCompatActivity {


    TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
    TableRow row; // création d'un élément : ligne
    TextView tv1,tv2; // création des cellules

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        String[] listeVideo;
        //recupere tous les fichiers contenant un certain noms
        File directory = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES), "StoryLand");
        File[] files = directory.listFiles();
    }
}
