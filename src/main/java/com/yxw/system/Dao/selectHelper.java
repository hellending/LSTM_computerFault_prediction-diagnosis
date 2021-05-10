package com.yxw.system.Dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class selectHelper {
    int code;
    String msg;
    int count;
    List<computer> list = new ArrayList<>();
}
