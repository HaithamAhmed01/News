package com.example.news.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class SourcesResponse{

	@SerializedName("sources")
	private List<SourcesItem> sources;

	@SerializedName("status")
	private String status;

	public String message;
	public String code;

	public void setSources(List<SourcesItem> sources){
		this.sources = sources;
	}

	public List<SourcesItem> getSources(){
		return sources;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"SourcesResponse{" + 
			"sources = '" + sources + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}