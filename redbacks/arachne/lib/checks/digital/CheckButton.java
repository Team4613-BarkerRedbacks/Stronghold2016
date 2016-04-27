package redbacks.arachne.lib.checks.digital;

import edu.wpi.first.wpilibj.buttons.Button;
import redbacks.arachne.lib.checks.Check.CheckDigital;
import redbacks.arachne.lib.commands.CommandRB;

/**
 * Checks the value of a button.
 * 
 * @author Sean Zammit
 */
public class CheckButton extends CheckDigital {
	Button button;
	
	/**
	 * @param button The button that is being checked.
	 * @param isTriggered Whether the value should be true.
	 */
	public CheckButton(Button button, boolean isTriggered) {
		super(isTriggered);
		this.button = button;
	}

	public boolean isTrue(CommandRB command) {
		return button.get() == type;
	}
}