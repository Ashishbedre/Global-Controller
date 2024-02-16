package com.example.demo.dto.BackendPackage;

public class VersionControlMicroDto {

	private String repo;
	private String tag;
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getRepo() {
		return repo;
	}
	public void setRepo(String repo) {
		this.repo = repo;
	}
	
	public VersionControlMicroDto(String tag, String repo) {
		super();
		this.tag = tag;
		this.repo = repo;
	}
	
	public VersionControlMicroDto() {
		super();
	}
	
	
}
