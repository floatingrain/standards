package com.harvestmoney.microdistribution.controller.advice.entity;

import lombok.Getter;

/**
 * 响应的基础信息类
 */
@Getter
public abstract class Response<D> {
	protected Integer httpCode;
	protected String status;
	protected D data;
}