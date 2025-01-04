package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "bussy")
public class bussy extends OpMode {
    private DcMotorEx linearSlide1;
    @Override
    public void init() {
        linearSlide1 = hardwareMap.get(DcMotorEx.class, "linearslide1");
        linearSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void start() {
        linearSlide1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        telemetry.addData("linearslide tick rotation", linearSlide1.getCurrentPosition());
        telemetry.update();
    }
}
