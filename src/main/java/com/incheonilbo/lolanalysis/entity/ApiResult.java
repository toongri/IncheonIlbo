package com.incheonilbo.lolanalysis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResult<T> {
    private T data;
}
