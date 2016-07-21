package redbacks.arachne.lib.navx;

import redbacks.arachne.lib.checks.Check;
import redbacks.arachne.lib.commands.CommandRB;

public class CheckHasTurned extends Check 
{
	@Override
	public boolean isTrue(CommandRB command) {
		return Math.abs(getYaw()) < 10;
	}

	public double getYaw() {
		return NavX.getYaw() + (NavX.getYaw() < -90 ? 360 : 0) - 180;
	}
}
