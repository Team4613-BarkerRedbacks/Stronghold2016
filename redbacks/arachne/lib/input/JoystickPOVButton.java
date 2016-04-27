package redbacks.arachne.lib.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * An extension of the Button class that is triggered by a set angle of the POV.
 * 
 * @author Sean Zammit
 */
public class JoystickPOVButton extends Button
{
    GenericHID m_joystick;
    int m_angle;

    /**
     * Creates a joystick POV 'button' for triggering commands.
     * 
     * @param joystick The controller which has the POV.
     * @param angle The required angle for the POV.
     */
    public JoystickPOVButton(GenericHID joystick, int angle) {
        m_joystick = joystick;
        m_angle = angle;
    }

    public boolean get() {
        return m_joystick.getPOV() == m_angle;
    }
}
