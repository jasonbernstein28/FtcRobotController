package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "StarterBotTeleOp", group = "TeleOp")
public class StarterBotTeleOp extends LinearOpMode {

    // four drive motors
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode() {

        // connects variables to physical motors on the robot
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        // right side motor mounted opposite = reverse
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // reset encoders, position tracking starts at zero
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // sets motors run without encoder so driver has direct power control
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // lets driver know robot is ready
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        // waits for driver to press play
        waitForStart();

        while (opModeIsActive()) {

            // read joystick inputs from controller
            double forward = -gamepad1.left_stick_y; // negative because up on stick returns negative value
            double rotate = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            // dead zone to prevent drift when joystick is barely touched
            if (Math.abs(forward) < 0.05) forward = 0;
            if (Math.abs(rotate) < 0.05) rotate = 0;
            if (Math.abs(strafe) < 0.05) strafe = 0;

            // hold right bumper for slow/precise mode, full speed by default
            double speed;
            if (gamepad1.right_bumper == true) {
                speed = 0.4; // slow mode
            } else {
                speed = 1.0; // full speed
            }

            // mecanum drive math, each wheel gets a mix of forward, rotate, and strafe
            frontLeft.setPower((forward + rotate + strafe) * speed);
            frontRight.setPower((forward - rotate - strafe) * speed);
            backLeft.setPower((forward + rotate - strafe) * speed);
            backRight.setPower((forward - rotate + strafe) * speed);

            // live data on driver hub screen for debugging
            telemetry.addData("Front Left", frontLeft.getPower());
            telemetry.addData("Front Right", frontRight.getPower());
            telemetry.addData("Back Left", backLeft.getPower());
            telemetry.addData("Back Right", backRight.getPower());
            if (gamepad1.right_bumper == true) {
                telemetry.addData("Speed Mode", "Slow");
            } else {
                telemetry.addData("Speed Mode", "Full");
            }
            telemetry.update();
        }
    }
}
