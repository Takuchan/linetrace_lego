package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorChangeListener;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.logger.LoggerThread;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;

public class TakumiCarWithListener extends AbstCar implements KeyListener, ColorChangeListener {
    private Boolean flagBoolean = false;
    private boolean isActive = false;
    private int detectedBlue = 0;
    
    public static void main(String[] args) {
        LCD.drawString("ButtonEventCar", 0, 0);
        TimeKeeper car = new TakumiCarWithListener();
        car.start();
    }

    public TakumiCarWithListener() {
        colorChecker = ColorCheckerThread.getInstance();
        driver = new TakumiGoodDriver("B", "C");
        navigator = new TakumiGoodLeftEdgeTracer();
        logger = LoggerThread.getInstance();
        logger.setCar(this);
        Button.ESCAPE.addKeyListener(this);
        Button.ENTER.addKeyListener(this);
        colorChecker.addColorChangeListener(this);
    }

    private void switchNavigator() {
        if (flagBoolean) {
            navigator = new TakumiGoodLeftEdgeTracer();
        } else {
            navigator = new TakumiGoodRightEdgeTracer();
        }
    }

    @Override
    public void run() {
        isActive = true;
        switchNavigator();
        while (this.isActive) {
            navigator.decision(colorChecker, driver);
        }
    }

    @Override
    public void keyPressed(Key k) {
        if (k == Button.ESCAPE) {
            this.isActive = false;
            this.driver.stop();
        } else if(k == Button.ENTER) {
            flagBoolean = !flagBoolean;
            switchNavigator();
        }
    }

    @Override
    public void keyReleased(Key k) {
    }

    @Override
    public void colorChangeDetected(int colorId) {
        if (colorId == Color.RED) {
            this.isActive = false;
            this.driver.stop();
        } else if (colorId == Color.BLUE) {
        	long startTime = System.currentTimeMillis();
            long endTime = 0;
        	if (detectedBlue == 0) {
        		endTime = startTime + 430;
        		this.driver.turnRightGently();
            	detectedBlue = detectedBlue + 1;
        	}else if(detectedBlue == 1) {
        		 endTime = startTime + 1000;
        		detectedBlue = detectedBlue + 1;
        		this.driver.turnLeftGently();
        	}else if(detectedBlue == 2) {
        		endTime = startTime + 1300;
        		detectedBlue = detectedBlue + 1;
        		this.driver.turnLeftGently();
        	}else if(detectedBlue == 3) {
        		endTime = startTime + 500;
        		this.driver.turnRightGently();
        	}

            while (System.currentTimeMillis()< endTime) {
            	 this.driver.goStraight();
                 this.driver.forward();
            }
            flagBoolean = !flagBoolean;
            switchNavigator();
        }
    }
}
