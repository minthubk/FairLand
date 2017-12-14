package storyland.storyland;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.Toast;
import android.widget.VideoView;


public class ListeVideoActivity extends BaseActivity {

    // partage de videos
    NfcAdapter mNfcAdapter;
    // Flag to indicate that Android Beam is available
    boolean mAndroidBeamAvailable  = false;
    // List of URIs to provide to Android Beam
    private Uri[] mFileUris = new Uri[10];
    // Instance that returns available files from this app
    private FileUriCallback mFileUriCallback;

    TableLayout table;
    TableRow row; // création d'un élément : ligne
    TextView tv1,tv2,tv3; // création des cellules

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        listeVideos();

        //setUpNfc();
    }

    private void setUpNfc() {
        // NFC isn't available on the device
        if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // If Android Beam isn't available, don't continue.
            mAndroidBeamAvailable = false;

            // Android Beam file transfer is available, continue
        } else {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

                    /*
         * Instantiate a new FileUriCallback to handle requests for
         * URIs
         */
            mFileUriCallback = new FileUriCallback();
            // Set the dynamic callback for URI requests.
            mNfcAdapter.setBeamPushUrisCallback(mFileUriCallback,this);
        }
    }

    /**
     * Callback that Android Beam file transfer calls to get
     * files to share
     */
    private class FileUriCallback implements
            NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback() {
        }
        /**
         * Create content URIs as needed to share with another device
         */
        @Override
        public Uri[] createBeamUris(NfcEvent event) {
            return mFileUris;
        }
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
                tv2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        // It's index
                        String text = ((TextView)((TableRow)view.getParent()).getChildAt(0)).getText().toString();
                        String filename = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES)+"/StoryLand/"+text;
                        Log.v("file", "ready to share " + filename);
                        File file = new File(filename);

                        Log.v("file_exists", String.valueOf(file.exists()));

                        // Uri uri = Uri.parse(filename));
                        Uri uri = FileProvider.getUriForFile(ListeVideoActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                file);


                        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                        intentShareFile.setType("video/*");
                        // intentShareFile.setType("application/pdf");
                        //intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+ filename));
                        //intentShareFile.setDataAndType(uri, "video/*");
                        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
                        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


                        // intentShareFile.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File...");
                        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                        startActivity(Intent.createChooser(intentShareFile, "Share File"));
                    }
                });
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
