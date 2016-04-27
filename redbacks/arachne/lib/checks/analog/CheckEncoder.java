package redbacks.arachne.lib.checks.analog;

import edu.wpi.first.wpilibj.Encoder;
import redbacks.arachne.lib.actions.Action;
import redbacks.arachne.lib.checks.Check.CheckAnalog;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks whether an encoder has reached a specific value.
 * 
 * @author Sean Zammit
 */
public class CheckEncoder extends CheckAnalog
{
	Encoder encoder;
	
	/**
	 * @param pulses The number of pulses the encoder must reach past the starting position in any direction.
	 * @param encoder The encoder itself.
	 */
	public CheckEncoder(double pulses, Encoder encoder) {
		super(true, pulses);
		this.encoder = encoder;
	}

	public boolean isTrue(CommandRB command) {
		return Math.abs(encoder.get()) > value && super.isTrue(command);
	}
	
	public void begin(CommandRB command, Action action) {
		super.begin(command, action);
		encoder.reset();
	}
}
