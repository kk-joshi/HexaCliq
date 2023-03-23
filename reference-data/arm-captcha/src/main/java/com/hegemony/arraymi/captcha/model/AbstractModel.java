package com.hegemony.arraymi.captcha.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

@SuppressWarnings("serial")
public abstract class AbstractModel implements Serializable {

	// TODO make use of these default columns

	@Column
	private Date createDate;

	@Column
	private Date createBy;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Date createBy) {
		this.createBy = createBy;
	}

}
