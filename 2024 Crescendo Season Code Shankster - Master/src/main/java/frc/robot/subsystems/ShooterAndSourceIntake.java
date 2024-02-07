//Additional code for this subsystem will need to be programmed in the RobotContainer file, also.

package frc.robot.subsystems;

//***************************** Imports will populate here automatically ***************************************/

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

//***************************** Subsystem class and it's main method ***************************************/

public class ShooterAndSourceIntake extends SubsystemBase { //Class and class name identified at top of code.  This class envelopes all the code in this file.
  private final CANSparkMax motorController22; //Instantiate motor controller 1 - CANSparkMax can be traded out for TalonSRX
  private final CANSparkMax motorController23;  //Only needed if you are instantiating a 2nd motor controller, otherwise remove.
  private final CommandXboxController xboxController; //Instantiate your video game controller.

  public ShooterAndSourceIntake(CANSparkMax motorController_22, CANSparkMax motorController_23) {  // CANSparkMax can be traded out for TalonSRX
    this.motorController22 = motorController_22; //Create motor controller 1.
    this.motorController23 = motorController_23;  //Create motor controller 2 or remove.
    motorController23.setInverted(true); //Only use if your 2nd motor needs to be inverted, otherwise remove or comment out.
    xboxController = new CommandXboxController(1);  //set your usb port for the video game controller.
  }

  //***************************** Other methods in subsystem ***************************************/

  public Command spinMotorOutAtAmp() {  //method to turn motor(s) forwards; 0.3 = 30% forwards.  Copy this method to make different powers or directions.
    return new RunCommand(() -> {
      motorController22.set(0.4); //include only this motor for a single motor. Set the value between 0 and 1.  Examples: 0.3 = 30%; -0.4 = 40% reverse.
      motorController23.set(0.4); //Use a 2nd motor or remove.
    }, this);
  }

  public Command spinMotorInFromSource() {  //method to turn motor(s) in reverse; -0.3 = 30% reverse.
    return new RunCommand(() -> {
      motorController22.set(-0.3);
      motorController23.set(-0.3);
    }, this);
  }
  
  public Command spinMotorOutAtSpeaker() {  //method to run motors at full power; 0.1 = 100%.
    return new RunCommand(() -> {
      motorController22.set(0.7);
      motorController23.set(0.7);
    }, this);
  }
  public Command stopMotors() {  // ALWAYS include a stopMotors command; 0.0 = 0% stop.
    return new RunCommand(() -> {
      motorController22.set(0.0);
      motorController23.set(0.0);
    }, this);
  }



//***************************** Button Returns for the RobotContaner File ***************************************/


  public boolean buttonACondition() {  /* This boolean method is created as a return for the button in the robotContainer file 
    where the button code is located. Change button assignment in this code and the RobotContainer file to be custom to each subsystem
    and custom to what the drivers want.  The controller layout is saved in a shared Google folder for drivers and programmers.  
    Ask for editing access if needed: https://drive.google.com/drive/folders/1zIW7uXTRO00WlAYfY4SCgTigg7jqk19Z?usp=drive_link */ 
    return xboxController.button(1) != null;
  }

  public boolean buttonXCondition() { //rename button or take out
    return xboxController.button(3) != null;
  }

  public boolean rightTriggerCondition() { //rename trigger/axis or take out.
    return xboxController.getRightTriggerAxis() > 0.1;
  }





//***************************** Static Command Classes used within the subsystem ***************************************/

  public static class SpinMotorInFromSourceCommand extends RunCommand { /*This is a command class within the original class.  
    You will need to change the class name.  Its not how we ordinarily code but chat GPT did it and it works so we will keep it. */
    private final ShooterAndSourceIntake subsystem;

    public SpinMotorInFromSourceCommand(ShooterAndSourceIntake subsystem) { //rename the method name custom to your subsystem.  Two public methods cannot be named the same.
      super(subsystem::spinMotorInFromSource, subsystem); //rename the method name custom to your subsystem.
      this.subsystem = subsystem;  //leave this.
      addRequirements(subsystem);  //leave this
    }

    @Override
    public void end(boolean interrupted) {
      subsystem.stopMotors(); //Make sure to include the .stopMotors() in the end method.
    }
  }

  public static class SpinMotorOutToAmpCommand extends RunCommand {  // Same as previous command class.
    private final ShooterAndSourceIntake subsystem;

    public SpinMotorOutToAmpCommand(ShooterAndSourceIntake subsystem) {
      super(subsystem::spinMotorOutAtAmp, subsystem);
      this.subsystem = subsystem;
      addRequirements(subsystem);
    }

    @Override
    public void end(boolean interrupted) {
      subsystem.stopMotors();
    }
  }

  public static class SpinMotorOutToSpeakerCommand extends RunCommand {  // Same as previous command class.
    private final ShooterAndSourceIntake subsystem;

    public SpinMotorOutToSpeakerCommand(ShooterAndSourceIntake subsystem) {
      super(subsystem::spinMotorOutAtSpeaker, subsystem);
      this.subsystem = subsystem;
      addRequirements(subsystem);
    }

    @Override
    public void end(boolean interrupted) {
      subsystem.stopMotors();
    }
  }
}
