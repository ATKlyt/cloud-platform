package cn.cat.platform;

import cn.cat.platform.common.Constant;
import cn.cat.platform.common.NettyConstant;
import cn.cat.platform.server.BootNettyServer;
import cn.cat.platform.utils.UploadFileUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

/**
 * @author linlt
 * @createTime 2020/3/4 9:52
 */
@SpringBootApplication
@MapperScan("cn.cat.platform.dao")
@ServletComponentScan
public class Application implements CommandLineRunner {

    @Autowired
    BootNettyServer bootNettyServer;

    /**
     *文件上传时使用相对路径报错，修改MultiPartFile的location
     * @return
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        //获取打包后jar包所在目录
        String jarPath = UploadFileUtil.getJarPath();
        //设置location
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(jarPath + Constant.UPLOAD_PATH);
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Override
    public void run(String... args) throws Exception {
        bootNettyServer.start(NettyConstant.SOCKET_PORT);
    }
}
