package com.yxw.system.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class computer {
    double cpuTem;
    double cpuFrequency;
    double fanSpeed;
    double cpuPower;
    double gpuTem;
    double sysTem;
    double powerTem;
    double gpuPower;
}
