package test;

import java.io.*;
import java.util.Arrays;

public class Demo1 {
    public static void main(String[] args) throws Exception {
        InputStream in = new FileInputStream("test/src/test/Furina.txt");
        OutputStream out = new FileOutputStream("test/src/test/Furina.jpg");
        byte[] buff = new byte[1024];
        int len;
        long begintime = System.currentTimeMillis();
        while((len = in.read(buff)) != -1) {
            out.write(buff, 0, len);
            System.out.println(Arrays.toString(buff));
            System.out.println(len);
        }
        System.out.println(len);
        long endtime = System.currentTimeMillis();
        System.out.println("耗时" + (endtime - begintime) + "ms");
        in.close();
        out.close();
    }
}