package com.jasper.framework.bean;

import com.jasper.framework.util.CastUtil;
import com.jasper.framework.util.CollectionUtil;
import com.jasper.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JasperWong on 2017-07-16.
 */
public class Param {

    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;


    /**
     * 请求参考对象
     * @param formParamList
     */
    public Param(List<FormParam> formParamList){
        this.formParamList=formParamList;

    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     */

    public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap=new HashMap<>();
        if (CollectionUtil.isNotEmpty(formParamList)) {
            for(FormParam formParam:formParamList){
                String fieldName=formParam.getFieldName();
                Object fieldValue=formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue=fieldMap.get(fieldName)+ StringUtil.SEPARATOR+fieldValue;
                }
                fieldMap.put(fieldName,fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap=new HashMap<>();
        if(CollectionUtil.isNotEmpty(fileParamList)){
            for(FileParam fileParam:fileParamList){
                String fieldName=fileParam.getFieldName();
                List<FileParam> fileParamList;
                if(fileMap.containsKey(fieldName)){
                    fileParamList=fileMap.get(fieldName);
                }else {
                    fileParamList=new ArrayList<FileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName,fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList(String fieldName){
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     */
    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList = getFileList(fieldName);
        if(CollectionUtil.isNotEmpty(fileParamList)&&fileParamList.size()==1){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 验证参数是否为空
     */
    public boolean isEmpty(){
        return CollectionUtil.isEmpty(formParamList)&& CollectionUtil.isEmpty(fileParamList);
    }

    public String getString(String name){
        return CastUtil.castString(getFieldMap().get(name));
    }

    public double getDouble(String name){
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    public long getLong(String name){
        return CastUtil.castLong(getFieldMap().get(name));
    }

    public int getInt(String name){
        return CastUtil.castInt(getFieldMap().get(name));
    }

    public boolean getBoolean(String name){
        return CastUtil.castBoolean(getFieldMap().get(name));
    }



}
    //    private Map<String,Object> paramMap;
//
//    public Param(Map<String,Object> paramMap){
//        this.paramMap=paramMap;
//    }
//
//    public long getLong(String name){
//        return CastUtil.castLong(paramMap.get(name));
//    }
//
//    public Map<String,Object> getMap(){
//        return paramMap;
//    }
//
//    public boolean isEmpty(){
//        return CollectionUtil.isEmpty(paramMap);


