package com.ksyun.ks3.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月16日 下午8:18:52
 * 
 * @description 
 **/
public class RequestUtils {
	public static List<String> subResource = Arrays.asList(new String[]{"acl", "lifecycle", "location", 
			"logging", "notification", "partNumber", 
            "policy", "requestPayment", "torrent", "uploadId", "uploads", "versionId",
            "versioning", "versions", "website", "delete", "thumbnail"});
	
	public static List<String> QueryParam = Arrays.asList(new String[]{"response-content-type",
		"response-content-language",
        "response-expires", "response-cache-control",
        "response-content-disposition", "response-content-encoding", 
        "width", "height"});
}
