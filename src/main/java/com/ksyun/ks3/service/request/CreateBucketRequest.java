package com.ksyun.ks3.service.request;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.ksyun.ks3.dto.AccessControlList;
import com.ksyun.ks3.dto.CannedAccessControlList;
import com.ksyun.ks3.dto.CreateBucketConfiguration;
import com.ksyun.ks3.dto.Grant;
import com.ksyun.ks3.dto.Permission;
import com.ksyun.ks3.http.HttpHeaders;
import com.ksyun.ks3.http.HttpMethod;
import com.ksyun.ks3.utils.HttpUtils;
import com.ksyun.ks3.utils.StringUtils;
import com.ksyun.ks3.utils.XmlWriter;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月15日 下午4:43:12
 * 
 * @description 新建bucket时的请求信息
 *              <p>
 *              Bucket是存放Object的容器，所有的Object都必须存放在特定的Bucket中。
 *              ，每个Bucket中可以存放无限多个Object。Bucket不能嵌套，每个Bucket中只能存放Object，
 *              不能再存放Bucket ，Bucket下的Object是一个平级的结构。
 *              <p>
 *              <p>
 *              Bucket的名称全局唯一且命名规则与DNS命名规则相同：
 *              <p>
 *              <ul>
 *              <li>长度3-63，</li>
 *              <li>不包含大写字母，不包含[‘ ’,\t,\r,\n]，不包含连续的’.’</li>
 *              <li>，’.’和’-’在bucket名称中不能相连，</li>
 *              <li>仅可包含. - 数字 小写字母，</li>
 *              <li>不以’.’或’-’结尾</li>
 *              </ul>
 **/
public class CreateBucketRequest extends Ks3WebServiceRequest {
	/**
	 * {@link CannedAccessControlList}设置新建的bucket的acl
	 */
	private CannedAccessControlList cannedAcl;
	/**
	 * 设置新建的bucket的acl
	 */
	private AccessControlList acl = new AccessControlList();
	/**
	 * Bucket存储地点配置
	 */
	private CreateBucketConfiguration config = null;

	public CreateBucketRequest(String bucketName) {
		this.setBucketname(bucketName);
	}

	public CreateBucketRequest(String bucketName,
			CannedAccessControlList cannedAcl) {
		this(bucketName);
		this.setCannedAcl(cannedAcl);
	}

	public CreateBucketRequest(String bucketName, AccessControlList acl) {
		this(bucketName);
		this.setAcl(acl);
	}

	public CreateBucketRequest(String bucketName,
			CreateBucketConfiguration.REGION region) {
		this(bucketName);
		config = new CreateBucketConfiguration(region);
	}

	public CannedAccessControlList getCannedAcl() {
		return cannedAcl;
	}

	public void setCannedAcl(CannedAccessControlList cannedAcl) {
		this.cannedAcl = cannedAcl;
	}

	public AccessControlList getAcl() {
		return acl;
	}

	public void setAcl(AccessControlList acl) {
		this.acl = acl;
	}

	@Override
	protected void configHttpRequest() {
		this.setHttpMethod(HttpMethod.PUT);
		if (this.config != null && this.config.getLocation() != null) {
			XmlWriter writer = new XmlWriter();
			writer.startWithNs("CreateBucketConfiguration")
					.start("LocationConstraint")
					.value(config.getLocation().toString()).end().end();
			this.setRequestBody(new ByteArrayInputStream(writer.toString()
					.getBytes()));
		}
		if (this.cannedAcl != null) {
			this.addHeader(HttpHeaders.CannedAcl.toString(),
					cannedAcl.toString());
		}
		if (this.acl != null) {
			this.getHeader().putAll(HttpUtils.convertAcl2Headers(acl));
		}
	}

	@Override
	protected void validateParams() throws IllegalArgumentException {
		if (StringUtils.validateBucketName(this.getBucketname()) == null)
			throw new IllegalArgumentException("bucket name is not correct");
	}

	public CreateBucketConfiguration getConfig() {
		return config;
	}

	public void setConfig(CreateBucketConfiguration config) {
		this.config = config;
	}
}
