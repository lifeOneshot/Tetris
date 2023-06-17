package Tetris;

import java.io.Serializable;

public class TetrisData implements Serializable{
	public static final int ROW = 20, COL = 10;
	
	private int data[][], line;
	
	long time;
	

	
	public TetrisData() {
		data = new int[ROW][COL];
		time = System.currentTimeMillis();
	}
	

	public int getAt(int x, int y) {
		if(x < 0 || x >= ROW || y < 0 || y >= COL) return 0;
		return data[x][y];
	}
	
	public void setAt(int x, int y, int v) {
		if (x >= 0 && x < ROW && y >= 0 && y < COL) {
	        data[x][y] = v;
		}
	}
	
	public int getLine() {
		return line;
	}
	
	public synchronized void removeLines() {
		NEXT:
			for(int i = ROW - 1; i >= 0; i--) {
				boolean done = true;
				for(int k = 0; k < COL; k++) {
					if(data[i][k] == 0) {
						done = false;
						continue NEXT;
					}
				}
				if(done) {
					line++;
					for(int x = i; x > 0; x--) {
						for(int y = 0; y < COL; y++) {
							data[x][y] = data[x-1][y];
						}
					}
					if(i != 0) {
						for(int y = 0; y < COL; y++) {
							data[0][y] = 0;
						}
					}
				}
			}
	}
	
	public void clear() {
		
		for(int i = 0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				data[i][k] = 0;
			}
		}

	}
	
	public void dump() {
		for(int i = 0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
	public boolean timeCtrl(long delay) {
		long systemTime =  System.currentTimeMillis();
		if (time + delay * 1000 <= systemTime) {
			time = System.currentTimeMillis();
			return true;
		}
		else {
			return false;
		}
	}
	
}
