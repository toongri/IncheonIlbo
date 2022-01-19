package com.incheonilbo.lolanalysis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiTotalResult<T> {
    private T data;
    private T totalCount;
}
