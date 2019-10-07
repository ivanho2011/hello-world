import java.io.*;
import java.util.Base64;

/**
 * Created by Ivan on 2019/10/7.
 */
public class ImageToBase64 {

    /**
     * ?????????????????????Base64????
     *
     * @param path ????
     * @return
     * @author sun
     * @date 2018?5?18? ??10:50:37
     */
    public static String imageToBase64(String path) {// ?????????????????????Base64????
        byte[] data = null;
        // ????????
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ?????Base64?? JDK8??
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);// ??Base64???????????
    }

    /**
     * ??????????Base64???????
     *
     * @param base64 ??Base64??
     * @param path   ????
     * @return
     * @author sun
     * @date 2018?5?18? ??10:50:22
     */
    public static boolean base64ToImage(String base64, String path) {// ??????????Base64???????
        if (base64 == null) { // ??????
            return false;
        }
        // JDK8??
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            // Base64??
            byte[] bytes = decoder.decode(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// ??????
                    bytes[i] += 256;
                }
            }
            // ??jpeg??
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean strToFile(String str, String file) {
        FileWriter fw = null;
        try {
//            fw = new FileWriter(file);
            StringBuilder sb = new StringBuilder(str);
//            fw.write(sb.reverse().toString());
            sb = sb.reverse();
            int pagesize = sb.length() / 32;
            for (int i = 0; i < sb.length(); i++) {
                if (i % pagesize == 0) {
                    if (null != fw) {
                        try {
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    fw = new FileWriter(String.format(file, i / pagesize));
                }
                fw.write(sb.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static String strFromFile(String file) {
        BufferedReader isr = null;
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; ;i++) {
                if (null != isr) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                isr = new BufferedReader(new FileReader(String.format(file, i)));
                sb.append(isr.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        String encodeStr = null;
//        encodeStr = imageToBase64("D:\\other\\whose.jpg");
//        System.out.println(strToFile(encodeStr, "D:\\other\\Separate$%d.txt"));
        encodeStr = strFromFile("D:\\workspace\\IDEA\\hello-world\\separate\\Separate$%d.txt");
        System.out.println(base64ToImage(encodeStr, "D:\\workspace\\IDEA\\hello-world\\separate\\whose.jpg"));
    }
}
