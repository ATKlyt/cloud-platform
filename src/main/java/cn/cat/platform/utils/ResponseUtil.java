package cn.cat.platform.utils;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author linlt
 * @CreateTime 2020/3/19 18:08
 */
public class ResponseUtil {

    public static void exportExcel(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "iso8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.close();
    }

    public static void exportCsv(HttpServletResponse response, File csvFile, String fileName) throws IOException {
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "iso8859-1");
        InputStream in = new FileInputStream(csvFile);
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType("application/csv;charset=UTF-8");
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }
}
