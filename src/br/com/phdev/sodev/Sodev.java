/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.phdev.sodev;

import br.com.phdev.sodev.driver.HCSR04;
import br.com.phdev.sodev.driver.MPU9150;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class Sodev {

    private static final int SENSOR_01 = 0;
    private static final int SENSOR_02 = 1;
    private static final int ALL_SENSORS = 2;

    private HCSR04 MOD_01;
    private HCSR04 MOD_02;
    private MPU9150 ACC_01;

    public Sodev(int modules, boolean showModuleInfo, boolean enableAccel) {
        try {
            switch (modules) {
                case SENSOR_01:
                    this.MOD_01 = new HCSR04(RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11);
                    this.MOD_01.setShowDistance(showModuleInfo);                    
                    break;
                case SENSOR_02:
                    this.MOD_02 = new HCSR04(RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_01);
                    this.MOD_02.setShowDistance(showModuleInfo);
                    break;
                case ALL_SENSORS:
                    this.MOD_01 = new HCSR04(RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11);
                    this.MOD_02 = new HCSR04(RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_01);
                    break;
            }
            if (enableAccel) {
                this.ACC_01 = new MPU9150();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        this.MOD_01.start();
        this.MOD_02.start();
    }

    public HCSR04 getMOD_01() {
        return MOD_01;
    }

    public HCSR04 getMOD_02() {
        return MOD_02;
    }

    public MPU9150 getACC_01() {
        return ACC_01;
    }

    public static void main(String[] args) {        
        
        int sensor = -1;
        boolean acc = false;
        boolean moduleInfo = false;        
        switch (args[0] + args[1]) {
            case "-s1":
                sensor = SENSOR_01;
                break;
            case "-s2":
                sensor = SENSOR_02;
                break;
            case "-sboth":
                sensor = ALL_SENSORS;
                break;            
        }
                
        if ((args[2] + args[3]).equals("-itrue"))
            moduleInfo = true;
        if ((args[4] + args[5]).equals("-aon"))
            acc = true;        
            
        if (sensor == -1)
            System.exit(0);
        
        Sodev sodev = new Sodev(sensor, moduleInfo, acc);
        sodev.start();

        /*
        double maxAngle = 0;
        double minAngle = 0;
        
        while (true) {
            List<Double> values = sodev.ACC_01.readAccel();
            //double y = (values.get(1) + 90) * -1;
            double y = values.get(1);
            /*
            if (y > maxAngle) {
                maxAngle = y;
                System.out.println("ANGULO MAXIMO: " + maxAngle);
            }
            if (y < minAngle) {
                minAngle = y;
                System.out.println("ANGULO MINIMO: " + minAngle);
            }

            System.out.println("Y: " + y);
            //System.out.println("X: " + values.get(0) + "    Y: " + ((values.get(1) + 90)) + "    Z: " + values.get(2));
            
        }
         */
    }

}
