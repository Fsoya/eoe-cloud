package top.sdaily.core.utils;

import java.util.UUID;

/**
 * Created by soya on 2016/12/5.
 */
public class IdGeneratorUtil {

    public static String fresh(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
