package model;

import java.time.LocalDateTime;

public class Stop {
	String name;
	LocalDateTime time;
	
	public Stop(String name, LocalDateTime time) {
		this.name = name;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDateTime getTime() {
		return time;
	}
}
