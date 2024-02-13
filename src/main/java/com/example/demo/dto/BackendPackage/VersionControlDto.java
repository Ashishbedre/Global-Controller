package com.example.demo.dto.BackendPackage;

public class VersionControlDto {

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
	
	public VersionControlDto(String tag, String repo) {
		super();
		this.tag = tag;
		this.repo = repo;
	}
	
	public VersionControlDto() {
		super();
	}
	
	
}
