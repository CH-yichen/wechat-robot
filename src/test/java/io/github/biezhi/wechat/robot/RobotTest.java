package io.github.biezhi.wechat.robot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import io.github.biezhi.wechat.Utils;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author biezhi
 *         18/06/2017
 */
public class RobotTest extends BaseTest {

    @Test
    public void testMoli() throws Exception {
        String url = "http://i.itpk.cn/api.php?question=你好";
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }

    @Test
    public void testTuling() throws Exception {
        String url = "http://www.tuling123.com/openapi/api";
        String apiKey = "213a19f54e1f42f7bbfbfafa532c6690";
        String secret = "6a906bece8691c8bONOFF";

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("key", apiKey);
        data.put("info", "你好");
        //获取时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        //生成密钥
        String keyParam = secret + timestamp + apiKey;
        String key = Md5.MD5(keyParam);

        //加密
        Aes mc = new Aes(key);
//        String dataJson = Utils.toJson(data);
        String dataJson = "{\"key\":\""+apiKey+"\",\"info\":\""+ "你好" +"\"}";

        String dataStr = mc.encrypt(dataJson);

        //封装请求参数
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("key", apiKey);
        json.put("timestamp", timestamp);
        json.put("data", dataStr);

        RequestBody requestBody = RequestBody.create(JSON, Utils.toJson(json));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
