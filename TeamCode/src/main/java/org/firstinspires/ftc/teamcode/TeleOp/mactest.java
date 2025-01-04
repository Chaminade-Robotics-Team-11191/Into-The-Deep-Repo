package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MacTest")
public class mactest extends OpMode {
    private DcMotor LeftFront;
    private DcMotor RightFront;
    private DcMotor LeftBack;
    private DcMotor RightBack;
    @Override
    public void init() {
        LeftFront = hardwareMap.get(DcMotor.class, "leftFront");
        RightFront = hardwareMap.get(DcMotor.class, "rightFront");
        LeftBack = hardwareMap.get(DcMotor.class, "leftBack");
        RightBack = hardwareMap.get(DcMotor.class, "rightBack");
    }

    @Override
    public void start() {
        LeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double z = gamepad1.right_stick_x;

        double theta = Math.atan2(y,x);
        double power = Math.hypot(x,y);
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        LeftFront.setPower(power * cos/max + z);
        LeftBack.setPower(power * sin/max + z);
        RightFront.setPower(power * sin/max - z);
        RightBack.setPower(power * cos/max - z);

        double[] motor = {LeftFront.getPower(), LeftBack.getPower(), RightBack.getPower(), RightFront.getPower()};

        if ((power + Math.abs(z)) > 1) {
            LeftFront.setPower(motor[0]/(power + Math.abs(z)));
            RightFront.setPower(motor[3]/(power + Math.abs(z)));
            LeftBack.setPower(motor[1]/(power + Math.abs(z)));
            RightBack.setPower(motor[2]/(power + Math.abs(z)));
        }
    }
}
