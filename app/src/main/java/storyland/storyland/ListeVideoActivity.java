package storyland.storyland;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.lang.String;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
        setTitle("Mes Vidéos");

        listeVideos();
    }

    private View.OnClickListener partageListener = new View.OnClickListener() {
        public void onClick(View view) {
            String text = ((TextView)((TableRow)view.getParent()).getChildAt(0)).getText().toString();
            String filename = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+"/StoryLand/"+text;
            Log.v("file", "ready to share " + filename);
            File file = new File(filename);

            Log.v("file_exists", String.valueOf(file.exists()));

            Uri uri = FileProvider.getUriForFile(ListeVideoActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);


            Intent intentShareFile = new Intent(Intent.ACTION_SEND);
            intentShareFile.setType("video/*");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
            intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    };

    private Drawable makeIcon(int icon, int size) {
        Drawable dr = getResources().getDrawable(icon);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, size, size, true));
        return d;
    }

    private void listeVideos() {
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

                // paramètres de layout
                LinearLayout.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                tv1 = new Button(this);
                String value = files[i].getName();// création cellule
                tv1.setText(value);
                tv1.setTextSize(12);
                tv1.setId(100+i);// ajout du texte
                tv1.setGravity(Gravity.CENTER); // centrage dans la cellule

                // adaptation de la largeur de colonne à l'écran :
                tv1.setLayoutParams(params);


                // idem 2ème cellule
                tv2 = new Button(this);
                tv2.setLayoutParams(params);
                tv2.setText("Share");
                tv2.setTextSize(12);
                tv2.setId(200+i);
                tv2.setGravity(Gravity.CENTER);
                tv2.setCompoundDrawablesRelativeWithIntrinsicBounds(makeIcon(R.mipmap.bouton_partager, 90), null, null, null);
                //tv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));


                // idem 3ème cellule
                tv3 = new Button(this);
                tv3.setText("DELETE");
                tv3.setLayoutParams(params);
                tv2.setTextSize(12);
                tv3.setId(300+i);
                tv3.setGravity(Gravity.CENTER);
                tv3.setCompoundDrawablesRelativeWithIntrinsicBounds(makeIcon(R.mipmap.bouton_supprimer, 50), null, null, null);
                //tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

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
                // click sur le partage
                tv2.setOnClickListener(partageListener);

                // clique sur delete
                tv3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        // It's index
                        String text = ((TextView)((TableRow)view.getParent()).getChildAt(0)).getText().toString();
                        Intent intent = new Intent(ListeVideoActivity.this, ListeVideoActivity.class);

                        File file = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+"/StoryLand/"+text); //Your id
                        if(file != null){
                            file.delete();
                        }
                        startActivity(intent);
                    }
                });

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
