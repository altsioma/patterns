package org.lessons;

public class MoveCommand {
    IMovingObject _obj;

    MoveCommand(IMovingObject obj) {
        _obj = obj;
    }

    public void execute() {
        this._obj.setLocation(Vector.plus(this._obj.getLocation(), this._obj.getVelocity()));
    }
}
