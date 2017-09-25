package com.jasper.framework.helper;

import com.jasper.framework.bean.FileParam;
import com.jasper.framework.bean.FormParam;
import com.jasper.framework.bean.Param;
import com.jasper.framework.util.CollectionUtil;
import com.jasper.framework.util.FileUtil;
import com.jasper.framework.util.StreamUtil;
import com.jasper.framework.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class UploadHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Apache Commons FileUpload 提供的servlet上传对象
     */
    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext){
        File respsitory=(File)servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload=new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,respsitory));
        int uploadLimit=ConfigHelper.getAppUploadLimit();
        if(uploadLimit!=0){
            servletFileUpload.setFileSizeMax(uploadLimit*1024*1024);
        }
    }

    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request) throws IOException{
        List<FormParam> formParamList=new ArrayList<>();
        List<FileParam> fileParamList=new ArrayList<>();
        try{
            Map<String,List<FileItem>> fileItemListMap=servletFileUpload.parseParameterMap(request);
            if(CollectionUtil.isNotEmpty(fileItemListMap)){
                for(Map.Entry<String,List<FileItem>> fileItemListEntry:fileItemListMap.entrySet()){
                    String fieldName=fileItemListEntry.getKey();
                    List<FileItem> fileItemList=fileItemListEntry.getValue();
                    if(CollectionUtil.isNotEmpty(fileItemList)){
                        for(FileItem fileItem:fileItemList){
                            if(fileItem.isFormField()){
                                String fieldValue=fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName,fieldValue));
                            }else{
                                String fileName=FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
                                if(StringUtil.isNotEmpty((fileName))){
                                    long fileSize=fileItem.getSize();
                                    String conetentType=fileItem.getContentType();
                                    InputStream inputStream=fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName,fileName,fileSize,conetentType,inputStream));
                                }
                            }
                        }
                    }
                }
            }
        }catch (FileUploadException e){
            LOGGER.error("create param failure",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList,fileParamList);
    }
    /**
     * 上传文件
     */
    public static void uploadFile(String basePath,FileParam fileParam){
        try{
            if(fileParam!=null){
                String filePath=basePath+fileParam.getFieldName();
                FileUtil.CreateFile(filePath);
                InputStream inputStream=new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream=new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream,outputStream);
            }
        }catch (Exception e){
            LOGGER.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }
    /**
     * 批量上传
     */
    public static void uploadFile(String basePath,List<FileParam> fileParamList){
        try{
            if(CollectionUtil.isNotEmpty(fileParamList)){
                for(FileParam fileParam:fileParamList){
                    uploadFile(basePath,fileParam);
                }
            }
        }catch (Exception e){
            LOGGER.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }
}
