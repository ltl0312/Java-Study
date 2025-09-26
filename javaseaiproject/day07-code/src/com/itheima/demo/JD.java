package com.itheima.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JD implements Switch{
    private String name;
    //状态:开或关
    private boolean status;//默认是false

    @Override
    public void press() {
        status = !status;
    }
}
