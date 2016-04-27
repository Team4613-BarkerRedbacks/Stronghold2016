package redbacks.arachne.lib.actions;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import redbacks.arachne.lib.checks.digital.CheckBoolean;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Action class used to set the value of a relay.
 * This action ends immediately, and sets the value of the relay on completion.
 * 
 * @author Sean Zammit
 */
public class ActionRelay extends Action
{
	private Relay relay;
	private int on;
	
	/**
	 * @param relay The relay being set by this command.
	 * @param on Whether the relay should be set to be on.
	 */
	public ActionRelay(Relay relay, boolean on) {
		super(new CheckBoolean(true));
		this.relay = relay;
		this.on = on ? 1 : 0;
	}
	
	/**
	 * Creates a relay action that switches the current position of the relay.
	 * 
	 * @param relay The relay being set by this command.
	 */
	public ActionRelay(Relay relay) {
		super(new CheckBoolean(true));
		this.relay = relay;
		this.on = 2;
	}
	
	public void onFinish(CommandRB command) {
		relay.set(
				on == 1 ? Value.kOn : 
				on == 0 ? Value.kOff : 
				
				relay.get() == Value.kOn ? Value.kOff : Value.kOn
		);
	}
}
