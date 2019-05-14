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

1.note_amoction.xml
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
