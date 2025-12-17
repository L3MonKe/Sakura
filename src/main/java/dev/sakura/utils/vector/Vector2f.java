package dev.sakura.utils.vector;


public final class Vector2f {
    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f vector) {
        this(vector.x, vector.y);
    }
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

}
