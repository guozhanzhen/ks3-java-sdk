package com.ksyun.ks3.exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;

import com.ksyun.ks3.utils.XmlReader;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月14日 下午5:55:37
 * 
 * @description 当抛出该异常时表示请求正常执行，但是Ks3服务器抛出异常，详见<a
 *              href="http://ks3.ksyun.com/doc/api/index.html"
 *              >http://ks3.ksyun.com/doc/api/index.html</a>最下面
 **/
public class Ks3ServiceException extends Ks3ClientException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5225806336951827450L;
	private Log log = LogFactory.getLog(Ks3ServiceException.class);

	/** 错误码*/
	private String errorCode;
	/**状态码*/
	private int statueCode;
	/**期望的状态码*/
	private String expectedStatueCode;
	/**错误*/
	private String message;
	/**用户请求的资源*/
	private String resource;
	private String requestId;

	public Ks3ServiceException(HttpResponse response, String expected) {
		super("");
		this.expectedStatueCode = expected;
		this.statueCode = response.getStatusLine().getStatusCode();
		try {
			Document document = new XmlReader(response.getEntity().getContent())
					.getDocument();
			try {
				message = document.getElementsByTagName("Message").item(0)
						.getTextContent();
			} catch (Exception e) {
				this.message = "unknow";
			}
			try {
				errorCode = document.getElementsByTagName("Code").item(0)
						.getTextContent();
			} catch (Exception e) {
				this.errorCode = "unknow";
			}
			try {
				resource = document.getElementsByTagName("Resource").item(0)
						.getTextContent();
			} catch (Exception e) {
				this.resource = "unknow";
			}
			try {
				requestId = document.getElementsByTagName("RequestId").item(0)
						.getTextContent();
			} catch (Exception e) {
				this.requestId = "unknow";
			}
		} catch (Exception e) {
		} finally {
			try {
				if (response.getEntity().getContent() != null)
					response.getEntity().getContent().close();
			} catch (IllegalStateException e) {
			} catch (IOException e) {
			}
		}
	}

	@Override
	public String toString() {
		return "Ks3ServiceException[RequestId:"+this.requestId+",Resource:" + resource + ",Statue code:"
				+ this.statueCode + ",Expected statue code:"
				+ this.expectedStatueCode + ",Error code:" + this.errorCode
				+ ",Error message:" + this.message + "]";
	}

	public String getErrorCode() {
		return errorCode;
	}

	public int getStatueCode() {
		return statueCode;
	}

	public String getMessage() {
		return message;
	}

	public String getResource() {
		return this.resource;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
