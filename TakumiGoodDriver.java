package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.driver.AbstDriver;

public class TakumiGoodDriver extends AbstDriver{
	final int SPEEDSHARPLY = 400;
	final int SPEEDHIGH = 250;
	final int SPEEDNORMAL = 200;
	final int SPEEDLOW = 100;
	
    private final int BASE_SPEED = 250;
    private final float KP = 0.7f;  // 比例係数
    private final float KI = 0.001f; // 積分係数
    private final float KD = 3.0f;   // 微分係数
    
    private float lastError = 0;
    private float integral = 0;
    
	public TakumiGoodDriver() {
		this("B","C");
	}
	
	public TakumiGoodDriver(String portLeft, String portRight) {
		setMotor(portLeft,portRight);
	}

	@Override
	public void goStraight() {
		// TODO 自動生成されたメソッド・スタブ
		setSpeed(SPEEDSHARPLY);
		
	}
    public void pidControl(float error) {
        integral += error;
        float derivative = error - lastError;
        
        float correction = (KP * error) + (KI * integral) + (KD * derivative);
        
        int leftSpeed = BASE_SPEED + (int)correction;
        int rightSpeed = BASE_SPEED - (int)correction;
        
        // スピードの上限・下限を設定
        leftSpeed = Math.min(Math.max(leftSpeed, 0), 400);
        rightSpeed = Math.min(Math.max(rightSpeed, 0), 400);
        
        setSpeed(leftSpeed, rightSpeed);
        forward();
        
        lastError = error;
    }

	@Override
	public void turnLeft() {
	    setSpeed(SPEEDNORMAL, SPEEDSHARPLY);
	}

	@Override
	public void turnRight() {
	    setSpeed(SPEEDSHARPLY, SPEEDNORMAL);
	}

	@Override
	public void turnLeftSharply() {
		// TODO 自動生成されたメソッド・スタブ
		setSpeed(SPEEDLOW,SPEEDHIGH);
	}

	@Override
	public void turnRightSharply() {
		// TODO 自動生成されたメソッド・スタブ
		setSpeed(SPEEDHIGH,SPEEDLOW);
		
	}

	@Override
	public void turnLeftGently() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void turnRightGently() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void increaseSpeed() {
		// TODO 自動生成されたメソッド・スタブ
		setSpeed(SPEEDSHARPLY);
	}

	@Override
	public void decreaseSpeed() {
		// TODO 自動生成されたメソッド・スタブ
		setSpeed(SPEEDNORMAL);
		
	}
	
 
}
