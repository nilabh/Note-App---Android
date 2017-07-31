package noteapp.nilabh.com.noteapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import noteapp.nilabh.com.noteapp.R;
import noteapp.nilabh.com.noteapp.customWidget.GrayScaleImageView;
import noteapp.nilabh.com.noteapp.dbUtil.DataBaseHandler;
import noteapp.nilabh.com.noteapp.model.NotesModel;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by nilabh on 11-07-2017.
 */
public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * The Db.
     */
    DataBaseHandler db;
    /**
     * The Save note.
     */
    Button saveNote, /**
     * The Open camera.
     */
    open_camera, /**
     * The Open gallery.
     */
    open_gallery;
    /**
     * The Input desc.
     */
    EditText input_desc, /**
     * The Input title.
     */
    input_title;
    /**
     * The Note img box.
     */
    LinearLayout note_img_box;
    /**
     * The Note img.
     */
    GrayScaleImageView note_img;
    /**
     * The Camera request.
     */
    int CAMERA_REQUEST = 101;
    /**
     * The Pick image.
     */
    int PICK_IMAGE = 102;
    /**
     * The My permissions request camera.
     */
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    /**
     * The M current photo path.
     */
    String mCurrentPhotoPath;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        saveNote = (Button) findViewById(R.id.save_note);
        open_gallery = (Button) findViewById(R.id.open_gallery);
        open_camera = (Button) findViewById(R.id.open_camera);
        open_camera.setOnClickListener(this);
        open_gallery.setOnClickListener(this);
        input_desc = (EditText) findViewById(R.id.input_desc);
        input_title = (EditText) findViewById(R.id.input_title);
        note_img_box = (LinearLayout) findViewById(R.id.note_img_box);
        note_img = (GrayScaleImageView) findViewById(R.id.note_img);
        note_img.setOnTouchListener(null);
        setClickObserver();
        initialiseDb();
    }

    private void setClickObserver(){
        Observable<OnClickEvent> clicksObservable
                = ViewObservable.clicks(saveNote);
        clicksObservable
                .subscribe(new Action1<OnClickEvent>() {
                    @Override
                    public void call(OnClickEvent onClickEvent) {
                        validateAndSave();
                    }
                });
    }

    private void validateAndSave(){
        String title = input_title.getText().toString();
        if(!title.isEmpty())
            title = title.trim();
        String desc = input_desc.getText().toString();
        if(!desc.isEmpty())
            desc = desc.trim();
        if(title.isEmpty()) {
            input_title.requestFocus();
            return;
        }
        if(desc.isEmpty()) {
            input_desc.requestFocus();
            return;
        }
        Date curDate = new Date();
        NotesModel notesModel = new NotesModel();
        notesModel.setTitle(title.trim());
        notesModel.setDescription(desc.trim());
        notesModel.setCreatedTime(curDate);
        if(mCurrentPhotoPath != null && !mCurrentPhotoPath.isEmpty())
            notesModel.setImgFileName(mCurrentPhotoPath);
        db.mNotesDao.addNote(notesModel);
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initialiseDb(){
        try{
            db = new DataBaseHandler(this);
            db.open();
        }catch (Exception e){

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open_camera:
                onCameraClick();
                break;
            case R.id.open_gallery:
                openGallery();
                break;
            default:
                break;
        }
    }

    /**
     * On camera click.
     */
    public void onCameraClick() {
        if ((int) Build.VERSION.SDK_INT < 23) {
            onCameraSuccess();
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                givePermission();
            } else {
                onCameraSuccess();
            }
        }
    }

    /**
     * Give permission.
     */
    public void givePermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        System.out.println("persmission : "+requestCode);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                System.out.println("persmission : "+grantResults.length+" : "+grantResults[0]);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCameraSuccess();
                } else {
                    Toast.makeText(this, "Choose from gallery", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    /**
     * On camera success.
     */
    public void onCameraSuccess() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    /**
     * Open gallery.
     */
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveToFile(photo);
        }else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                saveToFile(photo);
            }catch (Exception e){

            }
        }
    }

    private void saveToFile(final Bitmap photo){
        Observable<String> saveToFile = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    createImageFile(photo);
                    subscriber.onCompleted(); // Nothing more to emit
                }catch(Exception e){
                    subscriber.onError(e); // In case there are network errors
                }
            }
        });
        saveToFile
                .subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        // Called when the observable has no more data to emit
                        note_img_box.setVisibility(View.VISIBLE);
                        note_img.setImageBitmap(photo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Called when the observable encounters an error
                    }

                    @Override
                    public void onNext(String s) {
                        // Called each time the observable emits data
                        Log.d("MY OBSERVER", s);
                    }
                });
    }

    private void createImageFile(Bitmap photo) throws IOException {
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalCacheDir();
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            mCurrentPhotoPath = image.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(image);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }catch (Exception e){

        }
    }

}
