// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

//***************************** Subsystem class and it's main method ***************************************/

public class FloorIntakeArm extends SubsystemBase {
   private final TalonSRX motorcontroller18;
    private final CommandXboxController xboxController; //Instantiate your video game controller.
    private final DigitalInput rollerIntakeLimitSwitch = new DigitalInput(1);
    
    /** Creates a new FloorIntakeRoller. */
  public FloorIntakeArm() {
        motorcontroller18 = new TalonSRX(18);
        xboxController = new CommandXboxController(1);  //set your usb port for the video game controller.
  
      }
  
      //***************************** Other methods in subsystem ***************************************/

      public Command FloorIntakeArmUp() {
        return new RunCommand(() -> {
          motorcontroller18.set(TalonSRXControlMode.PercentOutput, 0.3); //include only this motor for a single motor. Set the value between 0 and 1.  Examples: 0.3 = 30%; -0.4 = 40% reverse.
      });
    }
      public Command FloorIntakeArmUpLimitSwitch(double upSpin) {
        return new RunCommand(() -> {
          if (upSpin > 0){
            if (!isLimitSwitchPressed()) {
              FloorIntakeArmUp();
              return;
            }
          }
          else {motorcontroller18.set(TalonSRXControlMode.PercentOutput, 0.3); //include only this motor for a single motor. Set the value between 0 and 1.  Examples: 0.3 = 30%; -0.4 = 40% reverse.
      }
    });
    }
    

      public Command FloorIntakeArmDown() {  //method to turn motor(s) out; 0.3 = 30% forwards.  Copy this method to make different powers or directions.
        return new RunCommand(() -> {
          motorcontroller18.set(TalonSRXControlMode.PercentOutput, -0.3); //include only this motor for a single motor. Set the value between 0 and 1.  Examples: 0.3 = 30%; -0.4 = 40% reverse.  
        });
      }
      
      
      public Command stopFloorIntakeArm() {  // ALWAYS include a stopMotors command; 0.0 = 0% stop.
        return new RunCommand(() -> {
          motorcontroller18.set(TalonSRXControlMode.PercentOutput,0.0);
        });
      }
    
//***************************** Button Returns for the RobotContaner File ***************************************/

        public boolean buttonLeftBumper() { //rename button or take out
          return xboxController.button(5) != null;
        }
        public boolean buttonRightBumper() { //rename button or take out
          return xboxController.button(6) != null;
        }
    
    
//***************************** Static Command Classes used within the subsystem ***************************************/


public static class FloorIntakeArmDownCommand extends RunCommand { /*This is a command class within the original class.  
  You will need to change the class name.  Its not how we ordinarily code but chat GPT did it and it works so we will keep it. */
  private final FloorIntakeArm subsystem;

  public FloorIntakeArmDownCommand(FloorIntakeArm subsystem) { //rename the method name custom to your subsystem.  Two public methods cannot be named the same.
    super(() -> subsystem.FloorIntakeArmDown(), subsystem); //rename the method name custom to your subsystem.
    this.subsystem = subsystem;  //leave this.
    addRequirements(subsystem);  //leave this.
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.stopFloorIntakeArm(); //Make sure to include the .stopMotors() in the end method.
  }
}
public boolean isLimitSwitchPressed() {
  // Check if the limit switch is pressed
  return !rollerIntakeLimitSwitch.get();
}
  
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
