package com.example.demo.dto;

public class TenantDto {

	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public TenantDto(String tenantId) {
		super();
		this.tenantId = tenantId;
	}

	public TenantDto() {
		super();
	}
	
	
}
