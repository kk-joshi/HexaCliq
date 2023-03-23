package com.hegemony.arraymi.captcha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Resource extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5561693350750643647L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	@Column
	private long userId;

	@Column
	@Lob
	private byte[] data;

	@Column
	@Enumerated(value = EnumType.STRING)
	private ContentType contentType;

	@Column
	private String mimeType;

	// for hibernate
	public Resource() {

	}

	public Resource(long userId, byte[] data, String mimeType, ContentType contentType) {
		super();
		this.userId = userId;
		this.data = data;
		this.mimeType = mimeType;
		this.contentType = contentType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
