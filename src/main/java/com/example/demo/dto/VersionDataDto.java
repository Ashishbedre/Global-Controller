package com.example.demo.dto;

import java.util.List;

public class VersionDataDto {

	private List<VersionControlDataDto> versionControl;

	public List<VersionControlDataDto> getVersionControl() {
		return versionControl;
	}

	public void setVersionControl(List<VersionControlDataDto> versionControl) {
		this.versionControl = versionControl;
	}

	public VersionDataDto(List<VersionControlDataDto> versionControl) {
		super();
		this.versionControl = versionControl;
	}

	public VersionDataDto() {
		super();
	}
	
	
}
