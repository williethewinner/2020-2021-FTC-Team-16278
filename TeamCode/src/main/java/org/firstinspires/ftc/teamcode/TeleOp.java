package org.firstinspires.ftc.teamcode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="MecanumDrive", group="Iterative Opmode")
public class TeleOp extends MecanumDrive {
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        // Read the joystick values, from -1 to 1
        double ly = gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;
        if ((gamepad2.right_trigger != 0) && !isLauncherOn) {
            isLauncherOn = true;
            launcherMotor.setPower(1);
        }
        if (gamepad2.right_bumper && isLauncherOn) {
            isLauncherOn = false;
            launcherMotor.setPower(0);
        }
        if ((gamepad2.left_trigger != 0) && !isFeederOn) {
            isFeederOn = true;
            feederMotor.setPower(1);
        }
        if (gamepad2.left_bumper && isFeederOn) {
            isFeederOn = false;
            feederMotor.setPower(0);
        }
        if (gamepad2.y ^ isLauncherServoActive) {
            isLauncherServoActive = !isLauncherServoActive;
            launcherServo.setPosition(isLauncherServoActive ? 0.75 : 0);
        }

        // Compute the commands to each of the four motors from the joystick values.
        double leftFrontMotorCommand = ly + lx - rx;
        double rightFrontMotorCommand = ly - lx + rx;
        double leftRearMotorCommand = ly - lx - rx;
        double rightRearMotorCommand = ly + lx + rx;

        // Limit the motor command by scaling by the max value if it exceeds 1
        double maxAbsoluteValue = Math.abs(leftFrontMotorCommand);
        maxAbsoluteValue = Math.max(maxAbsoluteValue, Math.abs(rightFrontMotorCommand));
        maxAbsoluteValue = Math.max(maxAbsoluteValue, Math.abs(leftRearMotorCommand));
        maxAbsoluteValue = Math.max(maxAbsoluteValue, Math.abs(rightRearMotorCommand));
        if (maxAbsoluteValue > 1) { // Make sure no motor is commanded faster than 1
            leftFrontMotorCommand /= maxAbsoluteValue;
            rightFrontMotorCommand /= maxAbsoluteValue;
            leftRearMotorCommand /= maxAbsoluteValue;
            rightRearMotorCommand /= maxAbsoluteValue;
        }

        // Make the motors move by sending the command
        leftFrontMotor.setPower(leftFrontMotorCommand);
        rightFrontMotor.setPower(rightFrontMotorCommand);
        leftRearMotor.setPower(leftRearMotorCommand);
        rightRearMotor.setPower(rightRearMotorCommand);

        // Show the wheel power.
        telemetry.addData("Motors", "LF:(%.2f) RF:(%.2f) LR:(%.2f) RR:(%.2f)", leftFrontMotorCommand, rightFrontMotorCommand, leftRearMotorCommand, rightRearMotorCommand);
    }
}