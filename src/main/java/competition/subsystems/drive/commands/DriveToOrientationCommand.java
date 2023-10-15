package competition.subsystems.drive.commands;

import javax.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;

public class DriveToOrientationCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;

    double currentAngle;
    double oldAngle;
    double goalAngle;
    double error;
    double power;
    double rotation;
    double startingAngle;

    double startingLeftToGoalAngle;
    @Inject
    public DriveToOrientationCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose) {
        this.drive = driveSubsystem;
        this.pose = pose;
    }

    public void setTargetHeading(double heading) {
        // This method will be called by the test, and will give you a goal heading.
        // You'll need to remember this target position and use it in your calculations.
        goalAngle = heading;
    }

    @Override
    public void initialize() {
        // If you have some one-time setup, do it here.
        startingAngle = pose.getCurrentHeading().getDegrees();
        startingLeftToGoalAngle = Math.abs(goalAngle - startingAngle);
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to turn to the target orientation
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!


        // if the angle from the left of the robot is greater than 180, robot will turn right instead.
        if (startingLeftToGoalAngle > 180) {
            goalAngle = 360 - startingLeftToGoalAngle;
            currentAngle = Math.abs(pose.getCurrentHeading().getDegrees()) - Math.abs(startingAngle);
            //if the currentAngle of the robot is positive, this math will ensure the currentAngle stays positive.
            if (pose.getCurrentHeading().getDegrees() > 0) {
                currentAngle = 360 - pose.getCurrentHeading().getDegrees() - Math.abs(startingAngle);
            }
            error = goalAngle - Math.abs(currentAngle);
            System.out.println(error);
            power = -1 * (error * 0.023 - rotation * 0.43);
        }
        else {
            currentAngle = pose.getCurrentHeading().getDegrees() - startingAngle;
            error = goalAngle - currentAngle;
            power = error * 0.023 - rotation * 0.43;
        }


        //giving power to motors
        drive.tankDrive(-power, power);
        rotation = currentAngle - oldAngle;
        oldAngle = currentAngle;



    }
// new branch test commit
    @Override
    public boolean isFinished() {
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
        double error;
        double rotation = currentAngle - oldAngle;
        error = goalAngle - currentAngle;
        if (Math.abs(error) < 0.1 && Math.abs(rotation) < 0.1) {

            drive.tankDrive(0,0);
            return true;
        }
        return false;
    }
}
