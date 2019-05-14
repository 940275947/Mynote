# Mynote
 ​      期中实验NotePad
 一.类之间的关系图
 ![image](https://github.com/940275947/Mynote/blob/master/app/image/image1.png)

二.主要功能：

1.NoteList中显示条目增加时间戳显示  

2.添加笔记查询功能（根据标题查询）

3.笔记的增删改查

4.自选笔记背景（三种背景选择）

5.改变字体背景
![image](https://github.com/940275947/Mynote/blob/master/app/image/image2.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image3.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image4.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image5.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image6.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image7.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image8.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image9.png)
![image](https://github.com/940275947/Mynote/blob/master/app/image/image10.png)
三.主要界面：

1.note_amoction.xml(主页面：主要包括searchview,listview 使用嵌套的线性布局)
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
    <SearchView
        android:id="@+id/serchnote"
        android:queryHint="请输入想要搜索的笔记名称"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"

        android:iconifiedByDefault="false"
        android:inputType="textFilter"></SearchView>
    <Button
        android:id="@+id/newnote"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="新建"/>
    <Button
        android:id="@+id/renew"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="刷新"/>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        <ListView
            android:id="@+id/listview"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </LinearLayout>

</LinearLayout>
2.listnote_item（listview 的装配页面，主要用于描述笔记的条目）
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/layoutlist"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
    android:id="@+id/notename"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAppearance="?android:attr/textAppearanceLarge"


    android:paddingLeft="5dip"

    android:singleLine="true"/>
    <TextView
        android:id="@+id/notetime"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceSmall"

        android:paddingLeft="5dip"
  />

</LinearLayout>
四.主要功能实现代码：
1.搜索功能
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
    
      public Cursor getById2(String id) {//根据点击事件获取id,查询数据库
        String[] args={"%"+id+"%"};
        System.out.println();
        return(getReadableDatabase()

                .rawQuery("SELECT  name, CreatedTime FROM note WHERE name like ?",
                        args));
    }
2.时间戳功能

        Date curdate=new Date(System.currentTimeMillis());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        s=simpleDateFormat.format(curdate);
 3.插入功能
 public void insert(String name, String content, String CreatedTime) {
        ContentValues cv=new ContentValues();

        cv.put("name", name);
        cv.put("content", content);
        cv.put("CreatedTime", CreatedTime);


        getWritableDatabase().insert("note", "name", cv);
    }
 4.删除功能
  public void delete(String name2) {


        String[] args={name2};
        System.out.println("nametitledelete:"+name2);
        getWritableDatabase().delete("note", "name=?",args);
    }
 5.修改功能
  public void update(String name,String newname, String content, String CreatedTime) {
        ContentValues cv=new ContentValues();
        String[] args={name};
        cv.put("name", newname);
        cv.put("content", content);
        cv.put("CreatedTime", CreatedTime);


        getWritableDatabase().update("note", cv, "name=?",
                args);
    }
 6查询功能
public Cursor getById(String id) {//根据点击事件获取id,查询数据库
        String[] args={id};

        return(getReadableDatabase()
                .rawQuery("SELECT  name, content, CreatedTime FROM note WHERE name=?",
                        args));
    }
   8.meu.xml 及相关功能
   <?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">


    <item
        android:id="@+id/item2"
        android:icon="@mipmap/ic_launcher"
        android:title="背景">
    <menu >
        <group android:id="@+id/backcolor1">
            <item android:id="@+id/backcolor11" android:title="背景一"/>
            <item android:id="@+id/backcolor12" android:title="背景二"/>
            <item android:id="@+id/backcolor13" android:title="背景三"/>
        </group>
    </menu>
    </item >

    <item
        android:id="@+id/menuItem_3"
        android:icon="@mipmap/ic_launcher"
        android:title="字体背景颜色">
        <menu >
            <group android:id="@+id/wordcolor">
                <item android:id="@+id/red" android:title="黄色"/>
                <item android:id="@+id/black" android:title="蓝色"/>
            </group>
        </menu>
    </item >

</menu>
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

            case R.id.size1:
                notename1.setTextSize(32);
                notetime1.setTextSize(16);
                listView.setAdapter(simpleAdapter);
                break;
            case R.id.size2:
                notename1.setTextSize(64);
                notetime1.setTextSize(32);
                listView.setAdapter(simpleAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    7.
