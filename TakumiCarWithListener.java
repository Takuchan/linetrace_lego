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
	
	
	public static void main(String[] args) {
		LCD.drawString("ButtonEventCar", 0, 0);
		TimeKeeper car = new TakumiCarWithListener();
		car.start();
	}

	public TakumiCarWithListener() {
		colorChecker = ColorCheckerThread.getInstance();
		driver = new TakumiGoodDriver("B", "C");
		navigator = new TakumiGoodLeftEdgeTracer();
		// ログ設定
		logger = LoggerThread.getInstance();
		logger.setCar(this);
		// listener登録
		Button.ESCAPE.addKeyListener(this);
		Button.ENTER.addKeyListener(this);
		colorChecker.addColorChangeListener(this);
	}
    private void switchNavigator() {
        if (flagBoolean) {
            navigator = new TakumiGoodRightEdgeTracer();
        } else {
            navigator = new TakumiGoodLeftEdgeTracer();
        }
    }


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		isActive = true;
        switchNavigator();
		// while(!Button.ESCAPE.isDown() && this.checkerColorSensor.getColorId() != Color.RED) {
        while (this.isActive) {
            // 現在のnavigatorを使用
            navigator.decision(colorChecker, driver);
        }
	
	}

	/**
	 *  キーが押された時の動作
	 *  @param k	押されたキーの種類 （lejos.hardwar e.Button で宣言された static フィールド）
	 */
	@Override
	public void keyPressed(Key k) {
		
		// ESCキーが押された時の動作
		if (k == Button.ESCAPE) {
			this.isActive = false;
			this.driver.stop();
		}else if(k == Button.ENTER) {
            flagBoolean = !flagBoolean;
            switchNavigator(); // navigatorを切り替え
		}
	}

	/**
	 * キーが離された時の動作．基本的に必要ないので，内容を記述する必要なし．
	 *  @param k	押されたキーの種類 （lejos.hardware.Button で宣言された static フィールド）
	 */
	@Override
	public void keyReleased(Key k) {
		// 必要ないので，何も記述しない
	}

	/**
	 * 色の変化の通知を受けた時の動作
	 * @param colorId 変化後の色の番号
	 */
	@Override
	public void colorChangeDetected(int colorId) {
		if (colorId == Color.RED) {
			this.isActive = false;
			this.driver.stop();
		}else if (colorId == Color.BLUE) {
			flagBoolean = !flagBoolean;
			switchNavigator();
		}
	}

	/**
	 * 動作継続条件
	 */
	private boolean isActive = false;

}