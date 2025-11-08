package javaprogramming.demo24readconfigurationfile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Test {
    // 建立键名映射关系
    private static final Map<String, String> KEY_MAPPING = new HashMap<>();
    static {
        KEY_MAPPING.put("module.type", "模块类型");
        KEY_MAPPING.put("module.version", "模块版本");
        KEY_MAPPING.put("component.name", "组件名称");
        KEY_MAPPING.put("component.inherit-compiler-output", "继承编译器输出");
        KEY_MAPPING.put("content.url", "内容URL");
        KEY_MAPPING.put("sourceFolder.url", "源文件夹URL");
        KEY_MAPPING.put("sourceFolder.isTestSource", "是否测试源码");
        KEY_MAPPING.put("orderEntry.jdk.name", "JDK名称");
        KEY_MAPPING.put("orderEntry.jdk.type", "JDK类型");
        KEY_MAPPING.put("orderEntry.sourceFolder.type", "顺序入口类型");
        KEY_MAPPING.put("orderEntry.sourceFolder.forTests", "用于测试");
    }

    public static void main(String[] args) {
        // 目标：编写程序，读取并解析一个配置文件（例如.properties文件）
        //Properties类继承 HashMap，所以它也可以作为Map使用
        Properties properties = new Properties();

        try (
                InputStream input = Test.class.getClassLoader().getResourceAsStream("programming.properties")
        ) {

            if (input == null) {
                System.out.println("找不到 application.properties 文件");
                return;
            }

            // 加载属性文件
            properties.load(input);

            // 读取并显示所有配置项（使用中文键名）
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String key = (String) entry.getKey();
                String chineseKey = KEY_MAPPING.getOrDefault(key, key);
                System.out.println(chineseKey + " = " + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
