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

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class Sodev {

    private HCSR04 MOD_01;
    private HCSR04 MOD_02;
    private MPU9150 ACC_01;
    
    public Sodev() {
        this.MOD_01 = new HCSR04(RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11);
        this.MOD_01.setShowDistance(true);
        this.MOD_02 = new HCSR04(RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_01);        
    }
    
    public void start() {
        this.MOD_01.start();
        this.MOD_02.start();
    }
    
    public static void main(String[] args) {
        new Sodev().start();
    }
    
}
