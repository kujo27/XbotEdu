package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;

public class TurnLeft90DegreesCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    double currentAngle;
    double oldAngle;
    double goalAngle;
    double error;
    double power;
    double rotation;
    double startingAngle;
    
    
    @Inject
    public TurnLeft90DegreesCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        startingAngle = pose.getCurrentHeading().getDegrees();
        goalAngle = 90;

    }

    @Override
    public void execute() {
        currentAngle = pose.getCurrentHeading().getDegrees() - startingAngle;
        
        // conditional that checks if the angle starts positive and ends up negative.
        if (pose.getCurrentHeading().getDegrees() < 0 && startingAngle > 0) {
            //makes the angle positive
            currentAngle = 360 + pose.getCurrentHeading().getDegrees() - startingAngle;
        }

        //error is goal angle - current angle
        error = goalAngle - currentAngle;


        
        //power assigned to motors is scaling down when error is less (error subtracting rotation)
        power = error * 0.023 - rotation * 0.43;
    

        //giving power to motors
        drive.tankDrive(-power, power);
        rotation = currentAngle - oldAngle;
        oldAngle = currentAngle;
    }

    @Override
    public boolean isFinished() {
        double rotation = currentAngle - oldAngle;
        double error = goalAngle - currentAngle;
        if (Math.abs(error) < 0.1 && Math.abs(rotation) < 0.1) {
            drive.tankDrive(0, 0);
            return true;
        }
        return false;
    }
}
