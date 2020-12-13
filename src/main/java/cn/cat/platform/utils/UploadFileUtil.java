package cn.cat.platform.utils;

import cn.cat.platform.common.Constant;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author linlt
 * @CreateTime 2020/3/20 10:52
 */
public class UploadFileUtil {


    /**
     * springboot项目jar发布，获取jar包所在目录路径
     * @return
     */
    public static String getJarPath(){
        ApplicationHome applicationHome = new ApplicationHome(UploadFileUtil.class);
        File jarFile = applicationHome.getSource();
        String jarPath = jarFile.getParentFile().toString();
        return jarPath;
    }

    /**
     * 上传文件
     * @param file 封装文件信息的Multipartfile对象
     * @return 图片的url
     */
    public static String uploadFile(MultipartFile file , String folderName){
        String path = getJarPath() + Constant.UPLOAD_PATH + "/" + folderName;
        File files = new File(path);
        if(!files.exists()){
            files.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-","");
        fileName = uuid + fileName;
        File targetFile = new File(path,fileName);
        try {
            /*
             * 如果不是绝对路径，StandardMultipartHttpServletRequest
             * 会在文件路径前加上location并在新路径下写入文件
             */
            file.transferTo(targetFile);
            return  path + "/" + fileName;
        } catch (IOException e) {
            return null;
        }
    }
}
