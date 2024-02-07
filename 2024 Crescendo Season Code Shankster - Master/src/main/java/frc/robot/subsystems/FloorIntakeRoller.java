// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

//***************************** Subsystem class and it's main method ***************************************/

public class FloorIntakeRoller extends SubsystemBase {
   private final TalonSRX motorController19 = new TalonSRX(19);
    //private final TalonSRX motorController20 = new TalonSRX(20);
    private final DigitalInput rollerIntakeLimitSwitch = new DigitalInput(0);
    //private boolean spinningIn = false;
    private final CommandXboxController xboxController = new CommandXboxController(1); //Instantiate your video game controller.

    /** Creates a new FloorIntakeRoller. */
  public FloorIntakeRoller() {
        //motorController20.setInverted(true); //Only use if your 2nd motor needs to be inverted, otherwise remove or comment out.
        
      }
  
      //***************************** Other methods in subsystem ***************************************/

      public Command spinFloorIntakeRollerOut() {  //method to turn motor(s) out; 0.3 = 30% forwards.  Copy this method to make different powers or directions.
        return new RunCommand(() -> {
          motorController19.set(TalonSRXControlMode.PercentOutput, 0.3); //include only this motor for a single motor. Set the value between 0 and 1.  Examples: 0.3 = 30%; -0.4 = 40% reverse.
          //motorController20.set(TalonSRXControlMode.PercentOutput, 0.3); //Use a 2nd motor or remove.
        });
      }

      public Command spinFloorIntakeRollerIn(double inSpin) {  //method to turn motor(s) out; 0.3 = 30% forwards.  Copy this method to make different powers or directions.
        return new RunCommand(() -> {
          if (inSpin > 0){
            if (!isLimitSwitchPressed()) {
              stopFloorIntakeRoller();
              return;
            }
            else {motorController19.set(TalonSRXControlMode.PercentOutput, -0.3); //include only this motor for a single motor. Set the value between 0 and 1.  Examples: 0.3 = 30%; -0.4 = 40% reverse.
            //motorController20.set(TalonSRXControlMode.PercentOutput, -0.3); //Use a 2nd motor or remove.
            } 
          } 
        });
      }
      
      public Command stopFloorIntakeRoller() {  // ALWAYS include a stopMotors command; 0.0 = 0% stop.
        return new RunCommand(() -> {
          motorController19.set(TalonSRXControlMode.PercentOutput,0.0);
          //motorController20.set(TalonSRXControlMode.PercentOutput,0.0);
        });
      }
    
  

//***************************** Button Returns for the RobotContainer File ***************************************/

        public boolean buttonBCondition() { //rename button or take out
          return xboxController.button(2) != null;
        }
        public boolean buttonYCondition() { //rename button or take out
          return xboxController.button(4) != null;
        }
    
    
//***************************** Static Command Classes used within the subsystem ***************************************/



public static class SpinFloorIntakeRollerOutCommand extends RunCommand { /*This is a command class within the original class.  
  You will need to change the class name.  Its not how we ordinarily code but chat GPT did it and it works so we will keep it. */
  private final FloorIntakeRoller m_FloorIntakeRoller;

  public SpinFloorIntakeRollerOutCommand(FloorIntakeRoller subsystem) { //rename the method name custom to your subsystem.  Two public methods cannot be named the same.
    super(subsystem::spinFloorIntakeRollerOut, subsystem); //rename the method name custom to your subsystem.
    this.m_FloorIntakeRoller = subsystem;  //leave this.
    addRequirements(subsystem);  //leave this.
  }

  @Override
  public void end(boolean interrupted) {
    m_FloorIntakeRoller.stopFloorIntakeRoller(); //Make sure to include the .stopMotors() in the end method.
  }
}





  public boolean isLimitSwitchPressed() {
      // Check if the limit switch is pressed
      return !rollerIntakeLimitSwitch.get();
  }

  /*public void setSpinningIn(boolean spinningIn) {
    this.spinningIn = spinningIn;
  }
  */
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
