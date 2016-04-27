package redbacks.arachne.lib.input;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * An extension of the Button class that is triggered by a set value of an axis.
 * 
 * @author Sean Zammit
 */
public class JoystickAxisButton extends Button
{
    JoystickAxis m_axis;
    boolean m_negative;
    double m_triggerPoint;

    /**
     * Creates a joystick axis 'button' for triggering commands.
     * 
     * @param joystick The controller which has the axis.
     * @param axis The axis on the controller.
     * @param negative Whether the trigger point is negative (top or left).
     * @param triggerPoint The value past which the button is triggered.
     */
    public JoystickAxisButton(JoystickAxis axis, boolean negative, double triggerPoint) {
        m_axis = axis;
        m_negative = negative;
        m_triggerPoint = Math.abs(triggerPoint);
    }

    public boolean get() {
        return m_negative ? m_axis.get() < -m_triggerPoint : m_axis.get() > m_triggerPoint;
    }
}
