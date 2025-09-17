package org.example.coding_convention.websocket.editor;
public class MousePosition {
    private String id;
    private int x;
    private int y;

    public MousePosition() {}

    public MousePosition(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
