package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="MecanumDrive", group="Iterative Opmode")
//@Disabled
public class MecanumDrive extends OpMode
{
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftRearMotor;
    private DcMotor rightRearMotor;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontMotor = hardwareMap.get(DcMotor.class, "Left Front");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "Right Front");
        leftRearMotor = hardwareMap.get(DcMotor.class, "Left Rear");
        rightRearMotor = hardwareMap.get(DcMotor.class, "Right Rear");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        leftRearMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightRearMotor.setDirection(DcMotor.Direction.REVERSE);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        // Read the joystick values, from -1 to 1
        double ly = gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

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
