package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "BasicTeleOp", group = "TeleOp")
public class BasicTeleOp extends LinearOpMode {

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    @Override
    public void runOpMode() {

        // Initialize hardware
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        // Reverse right side motors
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double drive = -gamepad1.left_stick_y;//gamepad = controller
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            frontLeft.setPower(drive + turn + strafe);
            frontRight.setPower(drive - turn - strafe);
            backLeft.setPower(drive + turn - strafe);
            backRight.setPower(drive - turn + strafe);

            telemetry.addData("Front Left", frontLeft.getPower());
            telemetry.addData("Front Right", frontRight.getPower());
            telemetry.addData("Back Left", backLeft.getPower());
            telemetry.addData("Back Right", backRight.getPower());
            telemetry.update();
        }
    }
}
