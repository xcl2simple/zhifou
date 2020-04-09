package cn.archforce.zhifou.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/7 17:38
 * @since JDK 1.8
 */
public class TextUtil {

    public static String saveToLocal(String content, String path, String fileName) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File txt = new File(path + "/" +fileName);
        if (!txt.exists()) {
            txt.createNewFile();
        }
        byte[] bytes = content.getBytes();
        FileOutputStream fos = new FileOutputStream(txt);
        fos.write(bytes);
        fos.flush();
        fos.close();
        return path + "/" + fileName;
    }

}
