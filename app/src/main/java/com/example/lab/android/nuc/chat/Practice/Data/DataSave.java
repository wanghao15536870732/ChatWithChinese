package com.example.lab.android.nuc.chat.Practice.Data;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataSave {
    private  static final String TAG = "DataSave";
    private Context context;

    public DataSave(Context context){
        this.context = context;
    }

    public  void save(String fileName,String content){
        FileOutputStream fileOutputStream =null;
         try{
             fileOutputStream = context.openFileOutput(fileName
                     ,Context.MODE_PRIVATE);
             fileOutputStream.write(content.getBytes());
         }catch (IOException e){
             e.printStackTrace();
         }finally {
             {
                 try{
                     if(fileOutputStream != null){
                         fileOutputStream.close();
                     }
                 }catch ( IOException ee){
                     ee.printStackTrace();
                 }
             }
         }

    }



    public String read(String fileNmae) {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            fileInputStream = context.openFileInput(fileNmae);
            int len = 0;
            byte[] buffer = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();
            while ((len = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            String string = new String(byteArrayOutputStream.toByteArray());
            Log.d(TAG, string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        byte[] data = byteArrayOutputStream.toByteArray();//获取内存中的所有书籍
        return new String(data);
    }

/**
 * 追加模式 创建出来的文件只能被本应用访问，其他应用无法访问 该文件 ，写入文件的内容会追加在原来所写的文件内容的后边。
 *
 * */
    public void saveAppend(String filename ,String content ){

        try{
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
