package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "testing")
public class testing extends LinearOpMode {
    private DcMotor left, right, back;
    @Override
    public void runOpMode() throws InterruptedException {
        left = hardwareMap.get(DcMotor.class, "leftFront");
        right = hardwareMap.get(DcMotor.class, "leftBack");
        back = hardwareMap.get(DcMotor.class, "rightFront");
        waitForStart();
        if (isStopRequested()) return;
        if (opModeIsActive()) {
            while(opModeIsActive()) {
                telemetry.addData("left", left.getCurrentPosition());
                telemetry.addData("right", right.getCurrentPosition());
                telemetry.addData("back", back.getCurrentPosition());
                telemetry.update();
            }
        }
    }
}
