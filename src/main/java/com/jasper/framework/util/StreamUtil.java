package com.jasper.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by JasperWong on 2017-07-17.
 */
public final class StreamUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream is){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
        }catch (Exception e){
            LOGGER.error("get string failure",e);
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}


