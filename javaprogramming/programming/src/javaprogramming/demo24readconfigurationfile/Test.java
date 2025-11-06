package javaprogramming.demo24readconfigurationfile;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        // 目标：编写程序，读取并解析一个配置文件（例如.properties文件）
        Properties properties = new Properties();

        try (
                InputStream input = Test.class.getClassLoader().getResourceAsStream("application.properties")
        ) {

            if (input == null) {
                System.out.println("找不到 application.properties 文件");
                return;
            }

            // 加载属性文件
            properties.load(input);

            // 读取并显示所有配置项
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
