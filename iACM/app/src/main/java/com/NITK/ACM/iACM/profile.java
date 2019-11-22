package com.NITK.ACM.iACM;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends AppCompatActivity {
    FirebaseAuth mAuth;
    CircleImageView viewImage;
    Context applicationContext;
    private TextView mDisplayDate;
    EditText profile_name_first;
    EditText SIG1, SIG2;
    TextView profile_dob;
    EditText profile_name_last, email, phone_number, username, achievements , batch;
    FirebaseDatabase firebaseDatabase;
    StorageReference photo_storage;
    DatabaseReference databaseReference, userdatabase;
    StorageReference storageReference;
    private FirebaseUser firebaseUser;
    Uri selectedImage;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String bool = "false";
    private Dialog dialog;
    Button edit, save, selectimage;
    UserProfile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);
        //
        profile = new UserProfile ();
        //

        //

        mAuth = FirebaseAuth.getInstance ();
        firebaseUser = mAuth.getCurrentUser ();
        //
        profile_name_first = (EditText) findViewById (R.id.shoppernameanifirstnego);
        profile_name_last = (EditText) findViewById (R.id.shoppernameanilastnego);
        email = (EditText) findViewById (R.id.shopperprofilemailaninego);
        phone_number = (EditText) findViewById (R.id.shopperphonenumberaninego);
        profile_dob = (TextView) findViewById (R.id.shopperprofiledobaninego);
        SIG1 = (EditText) findViewById (R.id.cat1v);
        SIG2 = (EditText) findViewById (R.id.cat2v);
        edit = (Button) findViewById (R.id.editbutton);
        save = (Button) findViewById (R.id.savebutton);
        selectimage = (Button) findViewById (R.id.selectphoto);
        username = (EditText) findViewById (R.id.user_username);
        achievements = (EditText) findViewById (R.id.rateachieve);
        batch = (EditText) findViewById (R.id.batch);



        // visibilty

        profile_name_first.setBackgroundResource (android.R.drawable.edit_text);
        profile_name_last.setBackgroundResource (android.R.drawable.edit_text);
        phone_number.setBackgroundResource (android.R.drawable.edit_text);
        email.setBackgroundResource (android.R.drawable.edit_text);
        username.setBackgroundResource (android.R.drawable.edit_text);
        profile_dob.setBackgroundResource (android.R.drawable.edit_text);
        achievements.setBackgroundResource (android.R.drawable.edit_text);
     SIG2.setBackgroundResource (android.R.drawable.edit_text);
        SIG1.setBackgroundResource (android.R.drawable.edit_text);
        batch.setBackgroundResource (android.R.drawable.edit_text);
        selectimage.setVisibility (View.GONE);
        //

        //
        profile_name_first.setEnabled (false);
        profile_name_last.setEnabled (false);
        email.setEnabled (false);
        profile_dob.setEnabled (false);
        username.setEnabled (false);
        phone_number.setEnabled (false);
        achievements.setEnabled (false);
        batch.setEnabled (false);

        SIG1.setEnabled (false);
        SIG2.setEnabled (false);
        profile_dob.setTextColor (getResources ().getColor (R.color.diabledgray));
        viewImage = (CircleImageView) findViewById (R.id.shopperprofilepicaninego);

        //

        // reading data

        userdatabase = FirebaseDatabase.getInstance ().getReference ().child ("Users").child (firebaseUser.getUid ());

        userdatabase.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profile = dataSnapshot.getValue (UserProfile.class);
                assert profile != null;
                profile_name_first.setText (profile.getFirstname ());
                profile_name_last.setText (profile.getLastname ());
                email.setText (profile.getEmail ());
                profile_dob.setText (profile.getDob ());
                username.setText (profile.getUserName ());

                phone_number.setText (profile.getPhone_number ());
                SIG1.setText (profile.getSIG1 ());
                SIG2.setText (profile.getSIG2 ());
                achievements.setText (profile.getAchievements ());
                try {


                    batch.setText (profile.getBatch ());
                }catch (NullPointerException e)
                {
                    batch.setText (" ");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //
        // fetch block

//        try{
//            fetch ();
//        }catch (NullPointerException e)
//        {
//            Log.v ("do something","hello");
//        }
        //

        //edit

        edit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                edit.setVisibility (View.GONE);
                save.setVisibility (View.VISIBLE);
                selectimage.setVisibility (View.VISIBLE);
                profile_name_first.setEnabled (true);
                profile_name_last.setEnabled (true);
                phone_number.setEnabled (true);
                SIG1.setEnabled (true);
                SIG2.setEnabled (true);
                username.setEnabled (true);
                profile_dob.setEnabled (true);
                email.setEnabled (true);
                achievements.setEnabled (true);

                profile_dob.setTextColor (getResources ().getColor (R.color.black));


                selectimage.setOnClickListener (new View.OnClickListener () {

                    @Override

                    public void onClick(View v) {

//                        selectImage ();
                        Toast.makeText (getApplicationContext (),"Function not available yet",Toast.LENGTH_SHORT).show ();

                    }

                });


                profile_dob.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance ();
                        int year = cal.get (Calendar.YEAR);
                        int month = cal.get (Calendar.MONTH);
                        int day = cal.get (Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog (profile.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day);

                        dialog.getDatePicker ().setMaxDate (new Date ().getTime ());
                        dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
                        dialog.show ();
                    }
                });

                mDateSetListener = new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d ("dsf", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                        String date = day + "/" + month + "/" + year;
                        profile_dob.setText (date);
                    }
                };


            }
        });


        //

        // save
        save.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                save.setVisibility (View.GONE);
                edit.setVisibility (View.VISIBLE);
                selectimage.setVisibility (View.GONE);
                profile_name_first.setEnabled (false);
                profile_name_last.setEnabled (false);
                phone_number.setEnabled (false);
                SIG1.setEnabled (false);
                SIG2.setEnabled (false);
                achievements.setEnabled (false);
                email.setEnabled (false);
                username.setEnabled (false);

                profile_dob.setEnabled (false);
                profile_name_first.setBackgroundResource (android.R.drawable.edit_text);
                profile_name_last.setBackgroundResource (android.R.drawable.edit_text);
                profile_dob.setTextColor (getResources ().getColor (R.color.diabledgray));
                profile.setFirstname (profile_name_first.getText ().toString ().trim ());
                profile.setLastname (profile_name_last.getText ().toString ().trim ());
                profile.setDob (profile_dob.getText ().toString ().trim ());
                profile.setPhone_number (phone_number.getText ().toString ().trim ());
                profile.setSIG1 (SIG1.getText ().toString ().trim ());
                profile.setSIG2 (SIG2.getText ().toString ().trim ());
                profile.setEmail (email.getText ().toString ().trim ());
                profile.setUserName (username.getText ().toString ().trim ());
                profile.setAchievements (achievements.getText ().toString ().trim ());
                update_detail ();
//                upload_image ();


            }
        });
        //
    }

    void update_detail() {
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Users");
        DatabaseReference myref = databaseReference.child (firebaseUser.getUid ());
        myref.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef ().child ("Firstname").setValue (profile.getFirstname ());
                dataSnapshot.getRef ().child ("Lastname").setValue (profile.getLastname ());
                dataSnapshot.getRef ().child ("UserName").setValue (profile.getUserName ());
                dataSnapshot.getRef ().child ("dob").setValue (profile.getDob ());
                dataSnapshot.getRef ().child ("Phone_number").setValue (profile.getPhone_number ());
                dataSnapshot.getRef ().child ("email").setValue (profile.getEmail ());
                dataSnapshot.getRef ().child ("SIG1").setValue (profile.getSIG1 ());
                dataSnapshot.getRef ().child ("SIG2").setValue (profile.getSIG2 ());
                dataSnapshot.getRef ().child ("Achievements").setValue (profile.getAchievements ());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void selectImage() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


        AlertDialog.Builder builder = new AlertDialog.Builder (profile.this);

        builder.setTitle ("Add Photo!");

        builder.setItems (options, new DialogInterface.OnClickListener () {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals ("Take Photo")) {

                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File (android.os.Environment.getExternalStorageDirectory (), "temp.jpg");

                    //  intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                    // check here for resolving error and you are good to go
                    Uri uri = FileProvider.getUriForFile (profile.this, BuildConfig.APPLICATION_ID + ".provider", f);
                    //  Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());
                    intent.addFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult (intent, 1);

                } else if (options[item].equals ("Choose from Gallery")) {

                    Intent intent = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult (intent, 2);


                } else if (options[item].equals ("Cancel")) {

                    dialog.dismiss ();

                }

            }

        });

        builder.show ();

    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v ("ssasad", "RESULTCODE:" + Integer.toString (requestCode));

        super.onActivityResult (requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                File f = new File (Environment.getExternalStorageDirectory ().toString ());

                for (File temp : f.listFiles ()) {

                    if (temp.getName ().equals ("temp.jpg")) {

                        f = temp;

                        // adding new peice of code here
                        selectedImage = Uri.fromFile (new File (f.toString ()));

                        ///

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options ();


                    bitmap = BitmapFactory.decodeFile (f.getAbsolutePath (),

                            bitmapOptions);


                    viewImage.setImageBitmap (bitmap);


                    String path = android.os.Environment

                            .getExternalStorageDirectory ()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete ();

                    OutputStream outFile = null;

                    File file = new File (path, String.valueOf (System.currentTimeMillis ()) + ".jpg");

                    try {

                        outFile = new FileOutputStream (file);

                        bitmap.compress (Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush ();

                        outFile.close ();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace ();

                    } catch (IOException e) {

                        e.printStackTrace ();

                    } catch (Exception e) {

                        e.printStackTrace ();

                    }

                } catch (Exception e) {

                    e.printStackTrace ();

                }

            } else if (requestCode == 2) {

///// changed uri selectimage  to global variable

                selectedImage = data.getData ();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver ().query (selectedImage, filePath, null, null, null);

                c.moveToFirst ();

                int columnIndex = c.getColumnIndex (filePath[0]);

                String picturePath = c.getString (columnIndex);

                c.close ();

                Bitmap thumbnail = (BitmapFactory.decodeFile (picturePath));

                Log.w ("pery", picturePath + "");

                viewImage.setImageBitmap (thumbnail);

            }

        }

    }

    private String getFileextension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver ();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton ();

        return mimeTypeMap.getExtensionFromMimeType (contentResolver.getType (uri)) ;
    }

    private void upload_image()
    {
        firebaseUser = FirebaseAuth.getInstance ().getCurrentUser ();
        storageReference = FirebaseStorage.getInstance ().getReference ("Users");
        databaseReference= FirebaseDatabase.getInstance ().getReference ();
        if(selectedImage != null)
        {
//            StorageReference mstorage = storageReference.child (System.currentTimeMillis ()+"."+getFileextension (selectedImage));
            StorageReference mstorage = storageReference.child (firebaseUser.getUid ()+"."+getFileextension (selectedImage));


            mstorage.putFile (selectedImage).addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler ();
                    handler.postDelayed (new Runnable () {
                        @Override
                        public void run() {
                            Toast.makeText (profile.this,"image uploaded",Toast.LENGTH_SHORT).show ();

                        }
                    },0);





                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText (profile.this,e.getMessage (),Toast.LENGTH_SHORT).show ();

                }
            }).addOnProgressListener (new OnProgressListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress=(100.0 * taskSnapshot.getBytesTransferred () / taskSnapshot.getTotalByteCount ());
                    Log.d ("progress" , String.valueOf (progress));
//                    dialog = new Dialog(profile.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.info_dialog);
//                    dialog.setTitle("Info");
//                    dialog.setCancelable(false);
//                    ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress_Bar);
//                    dialog.show ();
//                    if(progress == 100)
//                    {
//                        dialog.dismiss ();
//                        Log.d("closing diaolog box" , String.valueOf (progress));
//                        dialog.hide ();
//                        bool = "true";
//                       dialog.cancel ();
//                    }
                    setContentView (R.layout.dialog);
                    if (progress == 100)
                    {
                        startActivity (new Intent (profile.this,profile.class));
                    }

                }
            });

        }
        else
        {
            Toast.makeText (profile.this,"no photo",Toast.LENGTH_SHORT).show ();
        }
    }
    public void fetch()
    {
        try {

            final String location2 = firebaseUser.getUid () +"."+"null";
            String location = firebaseUser.getUid () + "." + "jpg";
            photo_storage.child (location).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString ();
                    Log.v ("image",imageURL);
                    Glide.with (getApplicationContext ()).load (imageURL).into (viewImage);
                }
            }).addOnFailureListener (new OnFailureListener () {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    photo_storage.child (location2).getDownloadUrl ().addOnSuccessListener (new OnSuccessListener <Uri> () {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageurl2 =uri.toString ();
                            Log.v ("url link",imageurl2);
                            Glide.with (getApplicationContext ()).load (imageurl2).into (viewImage);
                        }
                    }).addOnFailureListener (new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            });
        }catch (NullPointerException e)
        {
            Toast.makeText (profile.this,"here is the issue",Toast.LENGTH_LONG).show ();
        }


    }
}




