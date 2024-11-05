package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.driver.AbstDriver;

public class TakumiGoodDriver extends AbstDriver {
    private final int SPEED_STRAIGHT = 300;  // 直進速度
    private final int SPEED_TURN = 200;      // 通常旋回速度
    private final int SPEED_SHARP = 100;     // 急旋回速度
    
    public TakumiGoodDriver(String portLeft, String portRight) {
        setMotor(portLeft, portRight);
    }
    
    @Override
    public void goStraight() {
        setSpeed(SPEED_STRAIGHT, SPEED_STRAIGHT);
    }
    
    @Override
    public void turnLeft() {
        setSpeed(SPEED_TURN, SPEED_STRAIGHT);
    }
    
    @Override
    public void turnRight() {
        setSpeed(SPEED_STRAIGHT, SPEED_TURN);
    }
    
    @Override
    public void turnLeftSharply() {
        setSpeed(SPEED_SHARP, SPEED_STRAIGHT);
    }
    
    @Override
    public void turnRightSharply() {
        setSpeed(SPEED_STRAIGHT, SPEED_SHARP);
    }
    
    @Override
    public void turnLeftGently() {
        setSpeed((SPEED_STRAIGHT + SPEED_TURN) / 2, SPEED_STRAIGHT);
    }
    
    @Override
    public void turnRightGently() {
        setSpeed(SPEED_STRAIGHT, (SPEED_STRAIGHT + SPEED_TURN) / 2);
    }
    
    @Override
    public void increaseSpeed() {

    }
    
    @Override
    public void decreaseSpeed() {

    }
}
