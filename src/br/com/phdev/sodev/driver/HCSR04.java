/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.phdev.sodev.driver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;

/**
 *
 * @author Paulo Henrique GonÃ§alves Bacelar
 */
public class HCSR04 extends Thread{    
    
    private static int id;
    private boolean showDistance;

    private final GpioPinDigitalOutput trigger;
    private final GpioPinDigitalInput echo;
    private final GpioPinDigitalOutput led;
    
    private final GpioController controller;
    
    public HCSR04(Pin triggerPin, Pin echoPin, Pin ledPin) {
        this.controller = GpioFactory.getInstance();
        this.trigger = controller.provisionDigitalOutputPin(triggerPin);
        this.echo = controller.provisionDigitalInputPin(echoPin, PinPullResistance.PULL_DOWN);                        
        this.led = controller.provisionDigitalOutputPin(ledPin);
        id++;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                trigger.high();
                sleep((long) 0.01);
                trigger.low();
                
                long echoWait = System.nanoTime();
                
                while (echo.isLow()) {
                    if (((System.nanoTime() - echoWait) / 1000000000) > 2) {
                        if (led.isHigh())
                            led.low();
                        break;
                    }
                }
                
                long startTime = System.nanoTime();
                
                while (echo.isHigh()) {}
                
                long endTime = System.nanoTime();
                
                double distance = (((endTime - startTime) / 1e3) / 2) / 29.1;
                
                if (showDistance)
                    System.out.println("MOD_0" + id + " - Distancia: " + distance);
                
                if (distance < 40) {
                    if (led.isLow())
                        led.high();
                } else {
                    if (led.isHigh())
                        led.low();
                }
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShowDistance() {
        return showDistance;
    }

    public void setShowDistance(boolean showDistance) {
        this.showDistance = showDistance;
    }
    
    
    
}
