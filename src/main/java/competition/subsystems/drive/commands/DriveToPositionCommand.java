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
        currentPose = pose.getPosition();
        
        
        
        error = goal - currentPose;
        power = error * 1.35 - velocity * 5.6;
        drive.tankDrive(power, power);
        velocity = currentPose - oldPose;
        oldPose = currentPose;
    
        error = goal - currentPose;
    }
        

    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        double velocity = currentPose - oldPose;
        double error = goal - currentPose;
        if (error < 0.1 && error > -0.1 && velocity < 0.01 && velocity > -0.01) {
            drive.tankDrive(0,0);
            return true;

            
        }
        return false;
    
    }

}
