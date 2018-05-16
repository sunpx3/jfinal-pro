package com.sunpx.demo.controller;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.io.File;

/**
 * Created by Administrator on 2018/5/7 0007.
 */
public class AdminController extends Controller {

    public void toAdmin(){
        render("/admin/admin.html");
    }

    public void toUpload(){
        render("/WEB-INF/upload/upload.html");
    }
    public void toHome(){
        render("/admin/home.html");
    }


    public void fileUpload(){

        //该参数与form表单中文件域名称相同
        UploadFile uploadFile = getFile("files"   , "upload" ,50 * 1024 * 1024);
        String fileName = uploadFile.getFileName();
        System.out.println(fileName);

        renderHtml("<script>alert('上传成功!');location.href='/admin/toAdmin'</script>");
    }

    /**
     * 下载文件
     */
    public void dw(){

        String downloadPath = getRequest().getServletContext().getRealPath("download");
        File file = new File(downloadPath + "\\GaoEr.txt");

        renderFile(file);
    }

    /**
     * 导出excel
     */
    public void exportExcel(){
        String downloadPath = getRequest().getServletContext().getRealPath("download");

        //File file = new File(downloadPath + "\\测试下载.xls");

        //HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //HSSFSheet hssfSheet = hssfWorkbook.createSheet("test");
        // 创建行
        //HSSFRow row = hssfSheet.createRow(0);
       /* try {
            FileOutputStream fileOutputStreane = new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStreane);
            fileOutputStreane.flush();
            fileOutputStreane.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

       //renderFile(file)

    }
}
