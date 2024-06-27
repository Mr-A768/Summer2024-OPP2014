// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  public static REVPhysicsSim revPhysicsSim = new REVPhysicsSim();
  public static CANSparkMax leftDrive, leftFollower, rightDrive, rightFollower;

  
  /** Creates a new SparkMaxSimSubsystem. */
  public DriveSubsystem() {
    leftDrive = new CANSparkMax(0, MotorType.kBrushless);
    REVPhysicsSim.getInstance().addSparkMax(leftDrive, DCMotor.getNEO(0));

    leftFollower = new CANSparkMax(2, MotorType.kBrushless);
    leftFollower.follow(leftDrive);
    REVPhysicsSim.getInstance().addSparkMax(leftFollower, DCMotor.getNEO(0));

    rightDrive = new CANSparkMax(1, MotorType.kBrushless);
    REVPhysicsSim.getInstance().addSparkMax(rightDrive, DCMotor.getNEO(0));

    rightFollower = new CANSparkMax(3, MotorType.kBrushless);
    rightFollower.follow(rightDrive);
    REVPhysicsSim.getInstance().addSparkMax(rightFollower, DCMotor.getNEO(0));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    REVPhysicsSim.getInstance().run();
  }

  public void drive(double leftStickVal, double rightStickVal){
    leftDrive.getPIDController().setReference(1000 * leftStickVal, ControlType.kVelocity);
    rightDrive.getPIDController().setReference(1000 * rightStickVal, ControlType.kVelocity);
    REVPhysicsSim.getInstance().run();
  }

}
