package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.variable.x;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "testing servo")
public class setPosition extends LinearOpMode {
    private Servo i;
    @Override
    public void runOpMode() throws InterruptedException {
        i = hardwareMap.get(Servo.class, "jackismadidk");
        if (isStopRequested()) return;
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad2.y){
                i.setPosition(x);
            }
        }
    }
}
