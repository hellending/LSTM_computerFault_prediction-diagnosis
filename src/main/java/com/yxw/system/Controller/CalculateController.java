package com.yxw.system.Controller;

import com.yxw.system.Dao.computer;
import com.yxw.system.Dao.selectHelper;
import com.yxw.system.Service.CalcalateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.*;

import java.io.IOException;

/**
 * @author 叶欣委
 */
@Controller
public class CalculateController {

    @Autowired
    CalcalateService calcalateService;
    @Autowired
    static selectHelper ss;

    @RequestMapping("/back")
    public String back(){
        System.out.println("saas");
        return "index";
    }


    @RequestMapping("/calculate")
    public String calculate(@RequestParam String cpu_temp1 , @RequestParam String cpu_frequency1 , @RequestParam String fan_speed1 , @RequestParam String cpu_power1 , @RequestParam String gpu_tem1 , @RequestParam String system_temp1 , @RequestParam String power_tem1 , @RequestParam String gpu_power1 ,
                            @RequestParam String cpu_temp2 , @RequestParam String cpu_frequency2 , @RequestParam String fan_speed2 , @RequestParam String cpu_power2 , @RequestParam String gpu_tem2 , @RequestParam String system_temp2 , @RequestParam String power_tem2 , @RequestParam String gpu_power2 ,
                            @RequestParam String cpu_temp3 , @RequestParam String cpu_frequency3 , @RequestParam String fan_speed3 , @RequestParam String cpu_power3 , @RequestParam String gpu_tem3 , @RequestParam String system_temp3 , @RequestParam String power_tem3 , @RequestParam String gpu_power3 ,
                            @RequestParam String cpu_normal_frequency , @RequestParam String cpu_normal_rate , @RequestParam String gpu_normal_rate , @RequestParam String power_normal_rate , Model model
    ) throws IOException, InterruptedException {
        List<Double> list1 = new ArrayList<>();
        List<computer> list = new ArrayList<>();
        String info = calcalateService.calculate(
                cpu_temp1, cpu_frequency1, fan_speed1 , cpu_power1 , gpu_tem1 , system_temp1 , power_tem1 , gpu_power1 ,
                cpu_temp2, cpu_frequency2, fan_speed2 , cpu_power2 , gpu_tem2 , system_temp2 , power_tem2 , gpu_power2 ,
                cpu_temp3, cpu_frequency3, fan_speed3 , cpu_power3 , gpu_tem3 , system_temp3 , power_tem3 , gpu_power3 ,
                cpu_normal_frequency , cpu_normal_rate , gpu_normal_rate , power_normal_rate,list1);
        model.addAttribute("info",info);
        computer com1 = new computer();
        com1.setCpuTem(Double.parseDouble(cpu_temp1));
        com1.setGpuPower(Double.parseDouble(gpu_power1));
        com1.setSysTem(Double.parseDouble(system_temp1));
        com1.setGpuTem(Double.parseDouble(gpu_tem1));
        com1.setCpuFrequency(Double.parseDouble(cpu_frequency1));
        com1.setCpuPower(Double.parseDouble(cpu_power1));
        com1.setPowerTem(Double.parseDouble(power_tem1));
        com1.setFanSpeed(Double.parseDouble(fan_speed1));
        list.add(com1);
        computer com2 = new computer();
        com2.setCpuTem(Double.parseDouble(cpu_temp2));
        com2.setGpuPower(Double.parseDouble(gpu_power2));
        com2.setSysTem(Double.parseDouble(system_temp2));
        com2.setGpuTem(Double.parseDouble(gpu_tem2));
        com2.setCpuFrequency(Double.parseDouble(cpu_frequency2));
        com2.setCpuPower(Double.parseDouble(cpu_power2));
        com2.setPowerTem(Double.parseDouble(power_tem2));
        com2.setFanSpeed(Double.parseDouble(fan_speed2));
        list.add(com2);
        computer com3 = new computer();
        com3.setCpuTem(Double.parseDouble(cpu_temp3));
        com3.setGpuPower(Double.parseDouble(gpu_power3));
        com3.setSysTem(Double.parseDouble(system_temp3));
        com3.setGpuTem(Double.parseDouble(gpu_tem3));
        com3.setCpuFrequency(Double.parseDouble(cpu_frequency3));
        com3.setCpuPower(Double.parseDouble(cpu_power3));
        com3.setPowerTem(Double.parseDouble(power_tem3));
        com3.setFanSpeed(Double.parseDouble(fan_speed3));
        list.add(com3);
        computer com = new computer();
        com.setCpuTem(list1.get(0));
        com.setCpuFrequency(list1.get(1));
        com.setFanSpeed(list1.get(2));
        com.setCpuPower(list1.get(3));
        com.setGpuTem(list1.get(4));
        com.setSysTem(list1.get(5));
        com.setPowerTem(list1.get(6));
        com.setGpuPower(list1.get(7));
        list.add(com);
        ss.setList(list);
        return "result";
    }

    @RequestMapping("t")
    public String test(){
        return "result";
    }

    @RequestMapping("readExcelResponse")
    public selectHelper re(){
        ss.setCode(0);
        ss.setCount(1);
        ss.setMsg(":");
        return ss;
    }
}
