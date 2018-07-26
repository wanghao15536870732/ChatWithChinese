package com.example.lab.android.nuc.chat.Translation;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class getContent extends AppCompatActivity {
    //通用翻译API HTTP地址
    private final String url="http://api.fanyi.baidu.com/api/trans/vip/translate?";
    //APP ID
    public String appid="20180601000170165";
    //密钥
    public String password="hV3H2qtuOhw8ShIy0wzJ";
    //随机数
    private int salt = 1435660288;//Integer.valueOf((int)(System.currentTimeMillis()));
    //要翻译的内容
    private String query;
    //签名  加密后的字符串
    private String sign;
    // 加密前的原文
    private String src;

    //要翻译的语种
    private String from="auto";

    //翻译的目标语种
    private String to="zh";

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    //获取要翻译的内容
    public void setQuery(String query) {
        this.query = query;
        src=appid+query+salt+password;
    }




    /***
     * 设置用户自己的appid和密钥
     * @param appid
     * @param password
     */
    public void setValue(String appid, String password){
        this.appid = appid;
        this.password = password;
    }

    /****
     * 获取json返回的数据
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String Result() throws NoSuchAlgorithmException {
        String url=requestUrl();
        HttpGet httpGet=new HttpGet();
        String result=httpGet.get(url,null);
        Log.d("获取结果：",result);
        //JsonToString(result);
        return result;
    }


    /***
     * 获得完整的请求url
     * @return 完整的请求url
     * @throws NoSuchAlgorithmException
     */
    public String requestUrl() throws NoSuchAlgorithmException {

        String sign=stringToMD5(src);
        Log.d("MD5检验：",src);
        String requesturl=url+"q="+query+"&from="+from+"&to="+to+"&appid="+appid+"&salt="+salt+"&sign="+sign;
        return requesturl;
    }

    /***
     * 获取加密后的字符串
     * @param string 加密前的原文
     * @return  加密后的字符串
     */
    public static String stringToMD5(String string) {//MD5加密
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append( Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /***
     * 解析JSON  获取翻译结果
     * @return   翻译后的结果
     */
    public String JsonToString(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("trans_result");
            obj = array.getJSONObject(0);
            String word = obj.getString("dst");
            Log.d("翻译结果",word);
            return word;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
