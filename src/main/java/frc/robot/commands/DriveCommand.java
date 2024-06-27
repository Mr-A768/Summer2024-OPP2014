// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends Command {
  private DriveSubsystem m_DriveSubsystem;
  private DoubleSupplier leftStickSupplier, rightStickSupplier;

  /** Creates a new DriveCommand. */
  public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier leftStickSupplier, DoubleSupplier rightStickSupplier) {
    m_DriveSubsystem = driveSubsystem;
    this.leftStickSupplier = leftStickSupplier;
    this.rightStickSupplier = rightStickSupplier;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /* Apply stick deadband (eliminates inputs smaller than .1) */
    double leftStickVal = MathUtil.applyDeadband(leftStickSupplier.getAsDouble(), .1);
    double rightStickVal = MathUtil.applyDeadband(rightStickSupplier.getAsDouble(), .1);

    /* Exponential Drive (scales inputs so that smaller inputs are more precise) */
    leftStickVal = Math.copySign(Math.pow(leftStickVal, 2), leftStickVal);
    rightStickVal = Math.copySign(Math.pow(rightStickVal, 2), rightStickVal);

    /* Scale Sticks (ensures max speeds can be reached regardless of controller) */
    leftStickVal *= 1.1;
    leftStickVal = Math.abs(leftStickVal) > 1 ? Math.copySign(1, leftStickVal) : leftStickVal; // checks if input is extraneous

    rightStickVal *= 1.1;
    rightStickVal = Math.abs(rightStickVal) > 1 ? Math.copySign(1, rightStickVal) : rightStickVal; // checks if input is extraneous

    /* Send inputs to DriveSubsystem */
    m_DriveSubsystem.drive(leftStickVal, rightStickVal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
