package com.cohelp.server.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zgy
 * @create 2022-10-10 20:15
 */
@Data
public class Result implements Serializable {
    private String code;
    private Object data;
    private String message;
}
