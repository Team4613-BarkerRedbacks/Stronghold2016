package redbacks.robot.arm;

import redbacks.arachne.lib.actions.Action;

public enum ArmPosition {
	TOP(new ActionArmToTop()),
	CENTRE(new ActionArmToCentre()),
	BASE(new ActionArmToBase()),
	
	NOT_TOP(new ActionArmNotTop()),
	NOT_CENTRE(new ActionArmNotCentre()),
	NOT_BASE(new ActionArmNotBase());
	
	public final Action action;
	
	ArmPosition(Action action) {
		this.action = action;
	}
}
