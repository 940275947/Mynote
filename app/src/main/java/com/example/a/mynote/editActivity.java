package com.example.a.mynote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class editActivity extends Activity {
    private EditText notetile=null;
    private EditText maincontext=null;
    private Button baocun=null;
    private Button delete=null;
    private String intentitle=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_page);
        getWindow().setBackgroundDrawableResource(R.drawable.back2);
        notetile=(EditText)findViewById(R.id.notetitle) ;
        maincontext=(EditText)findViewById(R.id.maincontext) ;
        baocun=(Button)findViewById(R.id.save);
        delete=(Button)findViewById(R.id.delete);
        Intent intent=getIntent();
        intentitle=intent.getStringExtra("notetitle");
        notetile.setText(intentitle);
        DataOperation dataOperation = new DataOperation(editActivity.this, "note_db", null, 2);
        SQLiteDatabase db= dataOperation.getWritableDatabase();
        Cursor notesum=dataOperation.getById(intentitle);
        String notename1="无内容";
        while(notesum.moveToNext()){
             notename1=notesum.getString(1);
        }
        notesum.close();
        db.close();
        notetile.setText(intentitle);
        notetile.setGravity(Gravity.CENTER);
        maincontext.setText(notename1);
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataOperation dataOperation = new DataOperation(editActivity.this, "note_db", null, 2);
                SQLiteDatabase db= dataOperation.getWritableDatabase();

                String s;
                Date curdate=new Date(System.currentTimeMillis());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                s=simpleDateFormat.format(curdate);
                notetile=(EditText)findViewById(R.id.notetitle) ;
                maincontext=(EditText)findViewById(R.id.maincontext) ;
                String newtitle1,newContext;
                newtitle1=notetile.getText().toString();
                newContext=maincontext.getText().toString();
                dataOperation.update(intentitle,newtitle1,newContext,s);
                Intent intent=new Intent(editActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataOperation dataOperation = new DataOperation(editActivity.this, "note_db", null, 2);
                SQLiteDatabase db= dataOperation.getWritableDatabase();
                notetile=(EditText)findViewById(R.id.notetitle) ;

                String newtitle1;
                newtitle1=notetile.getText().toString();
                dataOperation.delete(newtitle1);
                Intent intent=new Intent(editActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
