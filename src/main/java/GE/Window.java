package GE;

public class Window {

    private int width, height;
    private String name;

    private static Window window = null;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.name = "2D Game Engine";
    }

    public static Window get(){
        if(window == null){
            window = new Window();
        }
        return window;
    }
}
