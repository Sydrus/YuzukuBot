package net.sydrus.yuzuku.Managers;

public class timeManager implements Runnable {

	public int day = 0;
	public int hour = 0;
	public int minute = 0;
	public int second = 0;
	private boolean Start = false;
	private Thread thread;

	public String toString() {
		return day + " Days " + hour + " Hours " + minute + " Minutes " + second + " Seconds";
	}

	public String getFormedTextOfUsed() {
		String toReturn = day + " Days " + hour + " Hours " + minute + " Minutes " + second + " Seconds";
		if (day > 0) {
			toReturn = day + " Days " + hour + " Hours " + minute + " Minutes " + second + " Seconds";
		} else {
			if (hour > 0) {
				toReturn = hour + " Hours " + minute + " Minutes " + second + " Seconds";
			} else {
				if (minute > 0) {
					toReturn = minute + " Minutes " + second + " Seconds";
				} else {
					toReturn = second + " Seconds";
				}
			}
		}
		return toReturn;
	}

	public void startCount() {
		if (Start) {
			return;
		}
		Start = true;
		thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings({ "deprecation" })
	public void stopCount() {
		if (!Start) {
			return;
		}
		Start = false;
		thread.stop();
		thread = null;
	}

	public void clear() {
		day = 0;
		hour = 0;
		minute = 0;
		second = 0;
	}

	public void run() {
		try {
			while (Start) {
				Thread.sleep(1000);
				second++;

				if (second > 59) {
					second = 00;
					minute = minute + 1;
				}

				if (minute == 59) {
					minute = 00;
					hour = hour + 1;
				}
				if (hour == 24) {
					day = day + 1;
				}
				if(!Start) {
					break;
				}
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}