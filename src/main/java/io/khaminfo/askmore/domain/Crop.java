package io.khaminfo.askmore.domain;

public class Crop {
	private int with;
	private int height;
	private int x;
	private int y;
	private int displayWidth;
	private int displayHeight;
	
	public int getDisplayWidth() {
		return displayWidth;
	}
	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}
	public int getDisplayHeight() {
		return displayHeight;
	}
	public void setDisplayHeight(int displayHeight) {
		this.displayHeight = displayHeight;
	}
	public int getWith() {
		return with;
	}
	public void setWith(int with) {
		this.with = with;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void setRatio(float x_ratio , float y_ratio ) {
		this.x = (int) (this.x * x_ratio);
		this.y = (int) (this.y * y_ratio);
		this.with = (int) (this.with * x_ratio);
		this.height = (int) (this.height * y_ratio);
	}
	

}
