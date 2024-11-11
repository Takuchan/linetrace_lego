package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.checker.ColorChecker;
import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.robotics.Color;

public class TakumiGoodRightEdgeTracer implements Navigator {
    private int sameColorCount = 0;
    private int lastColor = -1;

    @Override
    public void decision(ColorChecker colorChecker, MotorDriver driver) {
        if (!(driver instanceof TakumiGoodDriver)) {
            return;
        }
        
        TakumiGoodDriver speedDriver = (TakumiGoodDriver)driver;
        int currentColor = colorChecker.getColorId();
        
        if (currentColor == lastColor) {
            sameColorCount++;
        } else {
            sameColorCount = 0;
        }
        lastColor = currentColor;

        switch(currentColor) {
            case Color.WHITE:
                if (sameColorCount > 2) {
                    speedDriver.turnLeftSharply();
                } else {
                    speedDriver.turnLeft();
                }
                speedDriver.forward();
                break;
            case Color.BLACK:
                if (sameColorCount > 2) {
                    speedDriver.turnRightSharply();
                } else {
                    speedDriver.turnRight();
                }
                speedDriver.forward();
                break;
            default:
                speedDriver.goStraight();
                speedDriver.forward();
                sameColorCount = 0;
        }
    }
}