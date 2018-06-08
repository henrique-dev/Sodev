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

    private HCSR04 MOD_01;
    private HCSR04 MOD_02;
    private MPU9150 ACC_01;

    public Sodev() {
        try {
            this.MOD_01 = new HCSR04(RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11);
            this.MOD_01.setShowDistance(true);
            this.MOD_02 = new HCSR04(RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_01);
            this.ACC_01 = new MPU9150();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //this.MOD_01.start();
        //this.MOD_02.start();
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
        Sodev sodev = new Sodev();
        //sodev.start();
        
        double maxAngle;
        double minAngle;
        
        while (true) {
            List<Double> values = sodev.ACC_01.readAccel();
            System.out.println("X: " + values.get(0) + "    Y: " + (values.get(1) - 90) + "    Z: " + values.get(2));
        }
    }        

}
