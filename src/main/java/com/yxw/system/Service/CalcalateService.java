package com.yxw.system.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class CalcalateService {
    public String calculate(@RequestParam String cpu_temp1, @RequestParam String cpu_frequency1, @RequestParam String fan_speed1, @RequestParam String cpu_power1, @RequestParam String gpu_tem1, @RequestParam String system_temp1, @RequestParam String power_tem1, @RequestParam String gpu_power1,
                            @RequestParam String cpu_temp2, @RequestParam String cpu_frequency2, @RequestParam String fan_speed2, @RequestParam String cpu_power2, @RequestParam String gpu_tem2, @RequestParam String system_temp2, @RequestParam String power_tem2, @RequestParam String gpu_power2,
                            @RequestParam String cpu_temp3, @RequestParam String cpu_frequency3, @RequestParam String fan_speed3, @RequestParam String cpu_power3, @RequestParam String gpu_tem3, @RequestParam String system_temp3, @RequestParam String power_tem3, @RequestParam String gpu_power3,
                            @RequestParam String cpu_normal_frequency_string, @RequestParam String cpu_normal_rate_string, @RequestParam String gpu_normal_rate_string, @RequestParam String power_normal_rate_string, @RequestParam List<Double> list1
                            ) throws IOException, InterruptedException {


        String path = "D:\\calculate\\venv\\predict.py";

        String[] args = new String[] { "python", path,
                cpu_temp1, cpu_frequency1, fan_speed1 , cpu_power1 , gpu_tem1 , system_temp1 , power_tem1 , gpu_power1 ,
                cpu_temp2, cpu_frequency2, fan_speed2 , cpu_power2 , gpu_tem2 , system_temp2 , power_tem2 , gpu_power2 ,
                cpu_temp3, cpu_frequency3, fan_speed3 , cpu_power3 , gpu_tem3 , system_temp3 , power_tem3 , gpu_power3
        };
        System.out.println(Arrays.toString(args));
        // 执行py文件
        Process proc = Runtime.getRuntime().exec(args);
        System.out.println(proc);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        System.out.println(-1==in.read());
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        proc.waitFor();

        System.out.println(sb.toString());
        String[] s = sb.toString().substring(0,sb.length()-1).trim().split("\\s+");
        Double[] num = new Double[s.length];
        boolean[] flag = new boolean[s.length];
        Integer[] bit = new Integer[s.length];
        for(int i=0;i<s.length;i++){
            System.out.println(s[i]);
            if(s[i].contains("e")){
                num[i] = Double.parseDouble(s[i].substring(0,s[i].lastIndexOf("e")));
                System.out.println(s[i].substring(s[i].lastIndexOf("e")+1,s[i].lastIndexOf("e")+2)+"测试");
                flag[i] = s[i].substring(s[i].lastIndexOf("e")+1,s[i].lastIndexOf("e")+2)=="+";
                bit[i] = Integer.parseInt(s[i].substring(s[i].lastIndexOf("e")+1));
            }else{
                num[i] = Double.parseDouble(s[i]);
                flag[i] = true;
                bit[i] = 0;
            }
            if(flag[i]){
                for(int j = 0 ; j<bit[i] ; j++){
                    num[i] *= 10;
                }
            }else{
                for(int j = 0 ; j<bit[i] ; j++){
                    num[i] /= 10;
                }
            }

            System.out.println(num[i]+"----"+flag[i]+"------"+bit[i]);
        }
        for(double i:num){
            list1.add(i);
        }
        System.out.println("-------------");

        Double cpu_normal_frequency = Double.parseDouble(cpu_normal_frequency_string);
        Integer cpu_normal_rate = Integer.parseInt(cpu_normal_rate_string);
        Integer gpu_normal_rate = Integer.parseInt(gpu_normal_rate_string);
        Integer power_normal_rate = Integer.parseInt(power_normal_rate_string);

        String res = null;
        if (num[0] > 85 && num[1] > cpu_normal_frequency && num[2] > 1500 && num[3] > cpu_normal_rate * 1.6 &&
                num[4] > 30 && num[4] < 70 && num[5] < 60 && num[6] < 60 && num[7] < gpu_normal_rate)
        {
            res = "cpu当前温度过高 其他参数正常（风扇转速基本为全速，cpu功率为满载）  " +
                    "考虑的故障为cpu电压过高（停止超频，或降低电压）或建议更改cpu散热规格。";
        }else if ( num[2] > 1500 && num[4] > 80  && num[5] < 70 && num[6] < 60 )
        {
            res = "当前gpu的温度过高，运转正常，但影响到机箱整体温度升高（cpu的温度可能会随之增高，尽管它可能没有使用到全部性能）  " +
                    "考虑的故障为机箱整体的散热规格没有达到显卡要求的标准，导致机箱积热，会使得系统整体的能效大大降低。";
        }else if (num[2] > 1500 && num[5] > 65 && num[6] < 60)
        {
            res = "当前系统温度过高，其余部件正常工作，导致机箱整体温度变高  " +
                    "考虑的故障为机箱整体的通风情况不好，内部的积热无法快速散出，建议将机箱侧板拆下查看情况是否有所改善。";
        }else if (num[3] + num[7] > power_normal_rate)
        {
            res = "当前cpu的功率和gpu的功率加起来累计超过了电源的额定功率，电脑时常可能会出现黑屏的状况时候（所有部件正常工作）" +
                    "考虑的故障为设备整体的功耗要求已经超过了电源的最大负载能力，可能会触发电源的过流保护机制然后进行黑屏关机，这种情况对电脑元件和电源的损失很大，建议迅速更换功耗更小的部件或者推荐更换负载能力更大的电源。";
        }else if (num[0] > 70 && num[1] <= cpu_normal_frequency && num[2] > 1300)
        {
            res = "当前温度正常，cpu频率未到达额定频率时（此时风扇转速可能未拉满，cpu功率在额定浮动） " +
                    "考虑的故障为cpu电压过低  无法使cpu完全发挥正常的工作水平。造成了一定的性能浪费";
        }else if (num[0] > 85 && num[1] > cpu_normal_frequency && num[2] > 1500 && num[3] > cpu_normal_rate * 1.6 &&
                num[4] < 70 && num[5] < 60 && num[6] < 60 && num[7] < gpu_normal_rate * 0.7)
        {
            res = "当前cpu频率为满载 温度正常  显卡未达到额定功率且利用率不达百分之百（此时显卡温度偏低，且功耗不多)" +
                    "考虑的故障为cpu瓶颈，cpu的处理能力无法赶上gpu的输出带宽，造成了性能浪费。";
        }else
        {
            res = "当前所有的元件工作正常，电源温度偶尔会超过60度时，且电脑时常可能会出现黑屏的状况 " +
                    "考虑的故障为电源积热无法排出，导致电源过热触发过热保护机制，请检查电源的风扇是否正常工作，电源的风扇口是否被异物阻挡导致无法排除热量。";
        }

        return res;
    }
}
