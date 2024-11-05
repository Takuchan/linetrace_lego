package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.checker.ColorChecker;
import jp.ac.kanazawait.ep.majorlabB.driver.MotorDriver;
import jp.ac.kanazawait.ep.majorlabB.navigator.Navigator;
import lejos.robotics.Color;

public class TakumiGoodRightEdgeTracer implements Navigator{

	@Override
	public void decision(ColorChecker colorChecker, MotorDriver driver) {
		// TODO 自動生成されたメソッド・スタブ
		switch(colorChecker.getColorId()) {
		case Color.WHITE:
			driver.turnLeft();
			driver.forward();
			break;
		case Color.BLACK:
			driver.turnRight();
			driver.forward();
			break;
		default:
			driver.goStraight();
			driver.forward();
		}
	}
}
