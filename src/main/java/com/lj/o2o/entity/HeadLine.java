package com.lj.o2o.entity;

import java.util.Date;

/**
  *  头条
 * @author 贾成杰
 *
 */
public class HeadLine {
	private Long lineId;
	private String lineName;
	private String lineLink;
	private String lineImg;
	private Integer priotity;
	//0.不可用  1.可用   头条状态
	private Integer enableStatus;
	private Date ctrateTime;
	private Date lastEditTime;
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineLink() {
		return lineLink;
	}
	public void setLineLink(String lineLink) {
		this.lineLink = lineLink;
	}
	public String getLineImg() {
		return lineImg;
	}
	public void setLineImg(String lineImg) {
		this.lineImg = lineImg;
	}
	public Integer getPriotity() {
		return priotity;
	}
	public void setPriotity(Integer priotity) {
		this.priotity = priotity;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Date getCtrateTime() {
		return ctrateTime;
	}
	public void setCtrateTime(Date ctrateTime) {
		this.ctrateTime = ctrateTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
	
}
