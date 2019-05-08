
package com.example.a.mynote;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Calendar cal;
    private SearchView searchname;
    private Button newnote;
    private Button repage;
    private ListView listView;
    private List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
    private List<Map<String,Object>> list_map2 = new ArrayList<Map<String,Object>>();
    private  ArrayList<String> notetitle = new ArrayList<>();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_amoction);
        getWindow().setBackgroundDrawableResource(R.drawable.back1);
        listView=(ListView)findViewById(R.id.listview);
        newnote=(Button)findViewById(R.id.newnote);
        searchname=(SearchView)findViewById(R.id.serchnote);
        repage=(Button)findViewById(R.id.renew);

        DataOperation dataOperation = new DataOperation(MainActivity.this, "note_db", null, 2);
        SQLiteDatabase db= dataOperation.getWritableDatabase();

        String s;
        Date curdate=new Date(System.currentTimeMillis());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        s=simpleDateFormat.format(curdate);

 //     dataOperation.insert("笔记一","内容一",s);
  //     dataOperation.insert("笔记二","内容二",s);
        Cursor name=dataOperation.getAll();

        while(name.moveToNext()){
            Map<String, Object> items = new HashMap<String, Object>();
            String notename1=name.getString(0);
            notetitle.add(notename1);
            String notetime1=name.getString(2);
            items.put("notename1", notename1);  //放入头像， 根据下标获取数组
            items.put("notetime1", notetime1);      //放入名字， 根据下标获取数组
            list_map.add(items);
        }
        name.close();
        db.close();

        //为所有按钮对象设置监听器
        //依靠DatabaseHelper的构造函数创建数据库
        final SimpleAdapter simpleAdapter = new SimpleAdapter(
                MainActivity.this,
                list_map,
                R.layout.listnote_item,
                new String[]{"notename1", "notetime1"},
                new int[]{R.id.notename, R.id.notetime});

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast toast=Toast.makeText(MainActivity.this,notetitle.get(position),Toast.LENGTH_SHORT);
              //  toast.show();
                Intent intent=new Intent();
                String intenttitle=notetitle.get(position);
                intent.setClass(getApplicationContext(),editActivity.class);
                intent.putExtra("notetitle",intenttitle);
                startActivity(intent);
            }
        });
//笔记
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(list_map.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        simpleAdapter.notifyDataSetChanged();
                        DataOperation dataOperation = new DataOperation(MainActivity.this, "note_db", null, 2);
                        SQLiteDatabase db= dataOperation.getWritableDatabase();
                        String newtitle1=notetitle.get(position);

                        dataOperation.delete(newtitle1);
                        Toast.makeText(getBaseContext(), "删除文章", Toast.LENGTH_SHORT).show();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });

        newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity .this,newNote.class);
                startActivity(intent);
            }
        });
        repage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity .this,MainActivity.class);
                startActivity(intent);
            }
        });
        searchname.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击3搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "提交", Toast.LENGTH_SHORT).show();
                System.out.println("onQueryTextSubmit启动了");
                DataOperation dataOperation = new DataOperation(MainActivity.this, "note_db", null, 2);
                SQLiteDatabase db= dataOperation.getWritableDatabase();
                Cursor searchname2=dataOperation.getById2(query);
                while(searchname2.moveToNext()){
                    Map<String, Object> items2 = new HashMap<String, Object>();
                    String notename1=searchname2.getString(0);
                    String notetime1=searchname2.getString(1);
                    items2.put("notename1", notename1);  //放入头像， 根据下标获取数组
                    items2.put("notetime1", notetime1);      //放入名字， 根据下标获取数组
                    list_map2.add(items2);
                }
                TextView ceshi1=(TextView)findViewById(R.id.notename);

                MyAdapter  simpleAdapter2 = new MyAdapter (
                        MainActivity.this,
                        list_map2,
                        R.layout.listnote_item,
                        new String[]{"notename1", "notetime1"},
                        new int[]{R.id.notename, R.id.notetime});
                ceshi1.setTextColor(Color.parseColor("#ffff0000"));
                listView.setAdapter(simpleAdapter2);
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String s) {
                //do something3
                //当没有输入任何内容的时候清除结果，看实际需求
                if (TextUtils.isEmpty(s)) {  // 文本工具 检测是否为空，检测空，是输入文本改变 并且为空时触发，刚点击时候虽然为空，但是文本内容没有改变
                    // 设置 容器 的更新
                    DataOperation dataOperation = new DataOperation(MainActivity.this, "note_db", null, 2);
                    SQLiteDatabase db= dataOperation.getWritableDatabase();
                    String time;
                    Date curdate=new Date(System.currentTimeMillis());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time=simpleDateFormat.format(curdate);
                    Cursor searchname=dataOperation.getById2(s);

                    System.out.println("onQueryTextChange启动了");

                }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //super.onOptionsItemSelected(item);
        TextView notename1=(TextView)findViewById(R.id.notename);
        TextView notetime1=(TextView)findViewById(R.id.notetime);

        switch (item.getItemId()) {
            case R.id.backcolor11:
                getWindow().setBackgroundDrawableResource(R.drawable.back1);

                break;
            case R.id.backcolor12:
                getWindow().setBackgroundDrawableResource(R.drawable.back2);
                break;
            case R.id.backcolor13:
                getWindow().setBackgroundDrawableResource(R.drawable.back3);
                break;
            case R.id.red:
                listView.setBackgroundColor(Color.parseColor("#F8F8FF00"));
                break;
            case R.id.black:
                listView.setBackgroundColor(Color.parseColor("#ff0000ff"));
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}