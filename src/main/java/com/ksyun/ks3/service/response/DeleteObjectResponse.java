package com.ksyun.ks3.service.response;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月16日 下午3:47:35
 * 
 * @description 
 **/
public class DeleteObjectResponse extends Ks3WebServiceDefaultResponse<Boolean>{

	public int[] expectedStatus() {
		return new int[]{204};
	}

	@Override
	public void preHandle() {
	}
}
