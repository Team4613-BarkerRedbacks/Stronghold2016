package redbacks.arachne.lib.input;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * An extension of the Button class that allows for a list of buttons to be required to trigger the action.
 * 
 * @author Sean Zammit
 */
public class JoystickButtonList extends Button
{
	Button[] buttonList;

	/**
	 * Creates a 'button' holding a list of buttons that must all be pressed to trigger a command.
	 * 
	 * @param buttons
	 * The list of buttons.
	 */
	public JoystickButtonList(Button... buttons) {
		buttonList = buttons;
	}

	public boolean get() {
		for(int index = 0; index < buttonList.length; index++)
			if(!buttonList[index].get()) return false;
		return true;
	}
}
