

public class Component {
	
	private double startUpTime;
	private double shutDownTime;
	
	public Component(double startUpTime, double shutDownTime) {
		super();
		this.startUpTime = startUpTime;
		this.shutDownTime = shutDownTime;
	}

	public double getStartUpTime() {
		return startUpTime;
	}

	public double getShutDownTime() {
		return shutDownTime;
	}
	
	void startUp(){};
	void execute(){};
	void shutDown(){};
}
