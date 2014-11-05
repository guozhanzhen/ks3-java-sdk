package com.ksyun.ks3.service.response;

import com.ksyun.ks3.dto.PartETag;
import com.ksyun.ks3.http.HttpHeaders;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月23日 上午11:27:48
 * 
 * @description 
 **/
public class UploadPartResponse extends Ks3WebServiceDefaultResponse<PartETag>{

	public int[] expectedStatus() {
		return new int[]{200};
	}

	@Override
	public void preHandle() {
		result = new PartETag();
		result.seteTag(this.getHeader(HttpHeaders.ETag.toString()));
	}

}
