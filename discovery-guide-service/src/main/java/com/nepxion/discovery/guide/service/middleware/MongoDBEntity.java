package com.nepxion.discovery.guide.service.middleware;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MongoDBEntity")
public class MongoDBEntity {

	private String group;

	private String dataId;

	private String message;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MongodbEntity [group=" + group + ", dataId=" + dataId + ", message=" + message + "]";
	}

	
}
