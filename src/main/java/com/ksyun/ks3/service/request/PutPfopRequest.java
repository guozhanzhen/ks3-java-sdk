package com.ksyun.ks3.service.request;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ksyun.ks3.dto.Fop;
import com.ksyun.ks3.http.HttpHeaders;
import com.ksyun.ks3.http.HttpMethod;

import static com.ksyun.ks3.exception.client.ClientIllegalArgumentExceptionGenerator.notNull;

/**
 * @author lijunwei[lijunwei@kingsoft.com]  
 * 
 * @date 2015年2月2日 下午4:30:13
 * 
 * @description 添加数据处理任务
 **/
public class PutPfopRequest extends Ks3WebServiceRequest{

	/**
	 * 要进行的处理任务
	 */
	private List<Fop> fops = new ArrayList<Fop>();
	/**
	 * 数据处理任务完成后通知的url
	 */
	private String notifyURL;
	/**
	 * 
	 * @param bucketName 要处理的数据所在bucket
	 * @param key 要处理的数据的key
	 */
	public PutPfopRequest(String bucketName,String key){
		super.setBucketname(bucketName);
		super.setObjectkey(key);
	}
	/**
	 * 
	 * @param bucketName 要处理的数据所在bucket
	 * @param key 要处理的数据的key
	 * @param fops 数据处理指令
	 */
	public PutPfopRequest(String bucketName,String key,List<Fop> fops){
		super.setBucketname(bucketName);
		super.setObjectkey(key);
		this.setFops(fops);
	}
	@Override
	protected void configHttpRequest() {
		this.addParams("pfop", "");
		StringBuffer fopStringBuffer = new StringBuffer();
		for(Fop fop : fops){
			fopStringBuffer.append(fop.getCommand());
			if(!(StringUtils.isBlank(fop.getBucket())&&StringUtils.isBlank(fop.getKey()))){
				if(StringUtils.isBlank(fop.getBucket())){
					fopStringBuffer.append(String.format("|tag=saveas&object=%s",fop.getKey()));
				}else if(StringUtils.isBlank(fop.getKey())){
					fopStringBuffer.append(String.format("|tag=saveas&bucket=%s",fop.getBucket()));
				}else{
					fopStringBuffer.append(String.format("|tag=saveas&bucket=%s&object=%s",fop.getBucket(),fop.getKey()));
				}
			}
			fopStringBuffer.append(";");
		}
		String fopString = fopStringBuffer.toString();
		if(fopString.endsWith(";")){
			fopString = fopString.substring(0,fopString.length()-1);
		}
		this.addHeader(HttpHeaders.Fops, URLEncoder.encode(fopString));
		if(!StringUtils.isBlank(notifyURL))
			this.addHeader(HttpHeaders.NotifyURL, notifyURL);
		this.setHttpMethod(HttpMethod.PUT);
	}

	@Override
	protected void validateParams() throws IllegalArgumentException {
		if(StringUtils.isBlank(this.getBucketname()))
			throw notNull("bucketname");
		if(StringUtils.isBlank(this.getObjectkey()))
			throw notNull("objectkey");
		if(fops==null){
			throw notNull("fops");
		}else{
			for(Fop fop : fops){
				if(StringUtils.isBlank(fop.getCommand())){
					throw notNull("fops.command");
				}
			}
		}
		if(StringUtils.isBlank(notifyURL))
			throw notNull("notifyURL");
	}

	public List<Fop> getFops() {
		return fops;
	}

	public void setFops(List<Fop> fops) {
		this.fops = fops;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

}
