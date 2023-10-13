package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class DriveToPositionCommand extends BaseCommand {

    double goal;
    DriveSubsystem drive;
    PoseSubsystem pose;
    double error;
    double power;
    double velocity;
    double oldPose;
    double currentPose;



    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        
        
        
        //assigning the target position to a variable "goal"
        goal = position;
    
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        
        
        
        
        //setting the robots current position to a variable "currentPose"
        currentPose = pose.getPosition();
        
        //error is distance from goal and robots current position
        error = goal - currentPose;
        //asssinging power to robot that scales down when the error 
        power = error * 1.35 - velocity * 5.6;
        drive.tankDrive(power, power);
        // assigning the distance between the old position with current position as velocity (distance over time)
        velocity = currentPose - oldPose;
        //assigning current position as old position so robot positions keep updating
        oldPose = currentPose;
    }
        

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        
        //updating the velocity and error variables 
        double velocity = currentPose - oldPose;
        double error = goal - currentPose;
        
        //ends the program when error is very little, when robot is basically on the target position
        
        
        // if (error < 0.1 && error > -0.1 && velocity < 0.01 && velocity > -0.01) {
        if (error < 0.1 && error > -0.1) {
            drive.tankDrive(0,0);
            return true;

            
        }
        return false;
    
    }

}
