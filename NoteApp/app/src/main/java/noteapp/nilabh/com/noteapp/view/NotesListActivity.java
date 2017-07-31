package noteapp.nilabh.com.noteapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;

import noteapp.nilabh.com.noteapp.R;
import noteapp.nilabh.com.noteapp.adapter.NotesAdapter;
import noteapp.nilabh.com.noteapp.dbUtil.DataBaseHandler;
import noteapp.nilabh.com.noteapp.model.NotesModel;

import static noteapp.nilabh.com.noteapp.dbUtil.NoteSchemaInterface.COLUMN_ID;
import static noteapp.nilabh.com.noteapp.dbUtil.NoteSchemaInterface.COLUMN_TITLE;

/**
 * Created by nilabh on 11-07-2017.
 * List all notes created by user
 * Sort date and sort title buttons can be used to sort the notes in ascending and descending order
 */
public class NotesListActivity extends AppCompatActivity implements View.OnClickListener,NotesAdapter.OnItemClickListener{

    /**
     * The Db.
     */
    DataBaseHandler db;
    /**
     * The Fab.
     */
    FloatingActionButton fab;
    /**
     * The Notes models.
     */
    ArrayList<NotesModel> notesModels;
    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    /**
     * The constant REQUEST_CODE.
     */
    public static final int REQUEST_CODE = 1;
    /**
     * The Condition date.
     */
    String conditionDate = COLUMN_ID + " DESC";
    /**
     * The Condition title.
     */
    String conditionTitle = COLUMN_TITLE + " ASC";
    /**
     * The Default condition.
     */
    String defaultCondition = COLUMN_ID + " DESC";
    /**
     * The Date sort.
     */
    Button date_sort, /**
     * The Title sort.
     */
    title_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notesModels = new ArrayList<>();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        title_sort = (Button) findViewById(R.id.title_sort);
        date_sort = (Button) findViewById(R.id.date_sort);
        fab.setOnClickListener(this);
        date_sort.setOnClickListener(this);
        title_sort.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.notes_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NotesAdapter(notesModels,this);
        mRecyclerView.setAdapter(mAdapter);
        initialiseDb();
        loadNotes(conditionDate);
    }

    /**
     * Loads notes from db
     * @param condition
     */
    private void loadNotes(String condition){
        try{
            notesModels.clear();
            ArrayList<NotesModel> temp = db.mNotesDao.fetchAllNotes(condition);
            notesModels.addAll(temp);
            if(notesModels.size() == 0)
                addSampleNote();
            mAdapter.notifyDataSetChanged();
        }catch (Exception e){

        }
    }

    private void initialiseDb(){
        try{
            db = new DataBaseHandler(this);
            db.open();
        }catch (Exception e){

        }
    }

    private void addSampleNote(){
        NotesModel note = new NotesModel();
        note.setTitle(getResources().getString(R.string.sample_title));
        note.setDescription(getResources().getString(R.string.sample_desc));
        note.setCreatedTime(new Date());
        boolean isSaved = db.mNotesDao.addNote(note);
        loadNotes(defaultCondition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            loadNotes(defaultCondition);
        } catch (Exception ex) {
        }

    }

    /**
     * change in condition to order by date field
     */
    private void sortByDate(){
        if(conditionDate.contains("DESC"))
            conditionDate = COLUMN_ID + " ASC";
        else
            conditionDate = COLUMN_ID + " DESC";
        loadNotes(conditionDate);
    }

    /**
     * change in condition to order by title field
     */
    private void sortByTitle(){
        if(conditionTitle.contains("DESC"))
            conditionTitle = COLUMN_TITLE + " ASC";
        else
            conditionTitle = COLUMN_TITLE + " DESC";
        loadNotes(conditionTitle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                openAddNoteView();
                break;
            case R.id.date_sort:
                sortByDate();
                break;
            case R.id.title_sort:
                sortByTitle();
                break;
            default:
                break;
        }
    }

    private void openAddNoteView(){
        Intent intent = new Intent(this,
                AddNoteActivity.class);
        startActivityForResult(intent , REQUEST_CODE);
    }

    @Override
    public void onItemClick(int position, NotesModel object) {
        Intent intent = new Intent(this,
                ViewNoteActivity.class);
        intent.putExtra("NOTE_ID",object.getNoteId());
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
