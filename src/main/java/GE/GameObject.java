package GE;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;

    public GameObject(String name) {
        init(name, new Transform(), 0);
    }

    public GameObject(String name, Transform transform, int zIndex) {
        init(name, transform, zIndex);
    }

    public void init(String name, Transform transform, int zIndex) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.zIndex = zIndex;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if(componentClass.isAssignableFrom(c.getClass())) {
                return componentClass.cast(c);
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for(int i = 0; i<components.size(); i++) {
            if(componentClass.isAssignableFrom(components.get(i).getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        components.add(c);
        c.gameObject = this;
    }

    public void update(float dt){
        for(Component c : components) {
            c.update(dt);
        }
    }

    public void start(){
        for(Component c : components) {
            c.start();
        }
    }

    public int getZIndex() {
        return zIndex;
    }
}
