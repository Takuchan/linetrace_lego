package jp.ac.kanazawait.ep.takuchan;

import jp.ac.kanazawait.ep.majorlabB.car.AbstCar;
import jp.ac.kanazawait.ep.majorlabB.car.TimeKeeper;
import jp.ac.kanazawait.ep.majorlabB.checker.ColorCheckerThread;
import jp.ac.kanazawait.ep.majorlabB.logger.LoggerThread;
import jp.ac.kanazawait.ep.mmotoki.sample.MotokiCarWithLog;
import jp.ac.kanazawait.ep.mmotoki.sample.MotokiNaiveDriver;
import jp.ac.kanazawait.ep.mmotoki.sample.MotokiNaiveLeftEdgeTracer;
import lejos.hardware.Button;
import lejos.robotics.Color;

public class TakumiCarWithLog extends AbstCar {

	public static void main(String[] args) {
		TimeKeeper car = new MotokiCarWithLog();
		car.start();
	}

	public TakumiCarWithLog() {
		colorChecker = ColorCheckerThread.getInstance();
		driver = new MotokiNaiveDriver("B", "C");
		navigator = new MotokiNaiveLeftEdgeTracer();
		// ログ設定
		logger = LoggerThread.getInstance();
		logger.setCar(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (!Button.ESCAPE.isDown() && colorChecker.getColorId() != Color.RED) {
			navigator.decision(colorChecker, driver);
		}
	}

}
