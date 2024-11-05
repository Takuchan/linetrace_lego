package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.checker.ColorChecker;
import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.robotics.Color;

public class TakumiGoodLeftEdgeTracer implements Navigator {
    @Override
    public void decision(ColorChecker colorChecker, MotorDriver driver) {
        if (!(driver instanceof TakumiGoodDriver)) {
            return;
        }
        
        TakumiGoodDriver speedDriver = (TakumiGoodDriver)driver;
        
        switch(colorChecker.getColorId()) {
            case Color.WHITE:
                speedDriver.turnRight();
                speedDriver.forward();
                break;
            case Color.BLACK:
                speedDriver.turnLeft();
                speedDriver.forward();
                break;
            default:
                speedDriver.goStraight();
                speedDriver.forward();
        }
    }
}