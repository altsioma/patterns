package org.lessons;

public class RotateCommand {
    IRotatingObject _obj;

    RotateCommand(IRotatingObject obj) {
        _obj = obj;
    }

    public void execute() {
        this._obj.setAngle(Angle.plus(this._obj.getAngle(), this._obj.getAngularVelocity()));
    }
}
