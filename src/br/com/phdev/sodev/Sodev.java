/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.phdev.sodev;

import br.com.phdev.sodev.connection.ReadListener;
import br.com.phdev.sodev.connection.TCPClient;
import br.com.phdev.sodev.connection.WriteListener;
import br.com.phdev.sodev.driver.HCSR04;
import br.com.phdev.sodev.driver.MPU9150;
import br.com.phdev.sodev.driver.OnDetectorListener;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.net.Inet4Address;
import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class Sodev implements WriteListener, OnDetectorListener{

    private static final int SENSOR_01 = 0;
    private static final int SENSOR_02 = 1;
    private static final int ALL_SENSORS = 2;
    private static final int NO_SENSORS = 3;

    private HCSR04 MOD_01;
    private HCSR04 MOD_02;
    private MPU9150 ACC_01;
    
    private TCPClient client;
    private ReadListener readListener;

    public Sodev(int modules, boolean showModuleInfo, boolean enableAccel, String ip) {
        try {
            switch (modules) {
                case SENSOR_01:
                    this.MOD_01 = new HCSR04(RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11, "MOD_01", this);
                    this.MOD_01.setShowDistance(showModuleInfo);
                    break;
                case SENSOR_02:
                    this.MOD_02 = new HCSR04(RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_01, "MOD_02", this);
                    this.MOD_02.setShowDistance(showModuleInfo);
                    break;
                case ALL_SENSORS:
                    this.MOD_01 = new HCSR04(RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11, "MOD_01", this);
                    this.MOD_02 = new HCSR04(RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_01, "MOD_02", this);
                    break;
                case NO_SENSORS:
                    break;
            }
            if (enableAccel) {
                this.ACC_01 = new MPU9150();
            }
            if (ip != null) {
                client = new TCPClient(5000, ip, this);
                this.readListener = client.getReadListener();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (this.MOD_01 != null) {
            this.MOD_01.start();
        }
        if (this.MOD_02 != null) {
            this.MOD_02.start();
        }
        if (this.client != null) {
            this.client.start();
        }
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
        boolean connection = false;
        String ip = null;

        switch (args[0]) {
            case "1":
                sensor = SENSOR_01;
                break;
            case "2":
                sensor = SENSOR_02;
                break;
            case "both":
                sensor = ALL_SENSORS;
                break;
            case "off":
                sensor = NO_SENSORS;
                break;
            default:
                System.exit(-1);
        }

        switch (args[1]) {
            case "true":
                moduleInfo = true;
                break;
            case "false":
                moduleInfo = false;
                break;
            default:
                System.exit(-1);
        }

        switch (args[2]) {
            case "on":
                acc = true;
                break;
            case "off":
                acc = false;
                break;
            default:
                System.exit(-1);
        }

        switch (args[3]) {
            case "on":
                connection = true;
                break;
            case "off":
                connection = false;
                break;
            default:
                System.exit(-1);
        }

        if (connection) {
            ip = args[4];
            try {
                Inet4Address.getByName(ip);
            } catch (Exception e) {
                System.exit(-1);
            }
        }

        Sodev sodev = new Sodev(sensor, moduleInfo, acc, ip);
        sodev.start();

        if (acc) {
            while (true) {
                List<Double> values = sodev.ACC_01.readAccel();
                double y = (values.get(1) + 90) * -1;
                System.out.println("Y: " + y);
            }
        }
    }

    @Override
    public void write(String msg) {
        
    }

    @Override
    public void onDetect() {
        if (this.readListener != null)
            this.readListener.read("vibrate\n");
    }

}
