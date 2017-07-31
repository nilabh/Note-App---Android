package noteapp.nilabh.com.noteapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import noteapp.nilabh.com.noteapp.R;
import noteapp.nilabh.com.noteapp.customWidget.GrayScaleImageView;
import noteapp.nilabh.com.noteapp.dbUtil.DataBaseHandler;
import noteapp.nilabh.com.noteapp.model.NotesModel;
import noteapp.nilabh.com.noteapp.util.DateUtil;

/**
 * Created by nilabh on 11-07-2017.
 */
public class ViewNoteActivity extends AppCompatActivity {

    /**
     * The Db.
     */
    DataBaseHandler db;
    /**
     * The Note id.
     */
    int noteId;
    /**
     * The Note title.
     */
    TextView note_title, /**
     * The Note desc.
     */
    note_desc, /**
     * The Note date.
     */
    note_date;
    /**
     * The Note img box.
     */
    LinearLayout note_img_box;
    /**
     * The Note img.
     */
    GrayScaleImageView note_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteId = getIntent().getExtras().getInt("NOTE_ID");
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        note_title = (TextView) findViewById(R.id.note_title);
        note_desc = (TextView) findViewById(R.id.note_desc);
        note_date = (TextView) findViewById(R.id.note_date);
        note_img_box = (LinearLayout) findViewById(R.id.note_img_box);
        note_img = (GrayScaleImageView) findViewById(R.id.note_img);
        initialiseDb();
        setData();
    }

    private void setData() {
        NotesModel note = db.mNotesDao.fetchNotesById(noteId);
        note_title.setText(note.getTitle());
        note_desc.setText(note.getDescription());
        DateUtil dateUtil = new DateUtil();
        note_date.setText(dateUtil.getFormattedDate(note.getCreatedTime()));
        loadImageFromFile(note.getImgFileName());
    }

    private void loadImageFromFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            note_img.setImageBitmap(bitmap);
            note_img_box.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initialiseDb() {
        try {
            db = new DataBaseHandler(this);
            db.open();
        } catch (Exception e) {

        }
    }

    private void deleteNote(){
        db.mNotesDao.deleteNote(noteId);
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_delete_note:
                deleteNote();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
