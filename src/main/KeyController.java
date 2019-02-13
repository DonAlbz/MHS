package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyController implements Runnable {
	
	private Semaphore semaphore;
	BufferedReader in;
	
	
	public KeyController(Semaphore semaphore) {
		super();
		this.semaphore = semaphore;
		in=new BufferedReader(new InputStreamReader(System.in));
		
	}


	@Override
	public void run() {
			try {
				if(!semaphore.isStop() && in.read()!=-1)
					semaphore.setStop(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	public void stop() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}


	
	}
