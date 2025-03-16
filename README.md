# 🎮 2D Game Engine (OpenGL & LWJGL)  

*A lightweight, component-based 2D game engine built using Java, LWJGL (Lightweight Java Game Library), and OpenGL.*  

This engine is designed for efficient **sprite rendering**, **shader management**, and **asset loading**. While not intended for production, it serves as an **educational tool** for understanding 2D game engine architecture.  

---

## ✨ Features  

✔ **Component-Based Architecture** 
✔ **Batch Rendering**
✔ **Shader Management**
✔ **Texture Management**
✔ **Asset Pooling**

---

## ⚙️ How It Works  

### 🏗️ Component-Based Architecture  
The engine follows a **component-based design**, where `GameObject` entities are built using **reusable components**.  
For example:  

- `SpriteRenderer` → Handles rendering a sprite.  
- `RigidBody` → Adds physics-based movement.  
- `PlayerController` → Manages player input and movement.  

This modular design allows flexibility when creating new game objects.  

### 🚀 Batch Rendering (Performance Boost)  
Instead of sending **thousands of draw calls** to the GPU, the engine **batches** sprite rendering into **one** optimized call.  
- **Less CPU overhead** ✅  
- **Faster rendering speeds** ✅  
- **More objects on screen** ✅  

### 🎨 Shader System  
Shaders control how objects are drawn on the screen. This engine supports:  
✅ **Vertex & Fragment Shaders** – Customizable GLSL programs.  
✅ **Uniform Uploads** – Supports `matrices`, `vectors`, `floats`, and `textures`.  
✅ **Dynamic Shader Compilation** – Loads and compiles shaders at runtime.  

### 🖼️ Texture Management  
Textures are loaded from files using `stb_image` and managed via OpenGL.  
✔ Supports **RGB & RGBA** formats.  
✔ Implements **texture caching** to avoid duplicate loading.  
✔ Handles **pixel-perfect scaling** for crisp visuals.  

### 🎭 Asset Pooling  
Instead of **reloading assets**, the `AssetPool` **stores** and **reuses**:  
- 🔹 **Shaders**  
- 🔹 **Textures**  
- 🔹 **Spritesheets**  

This reduces memory usage and improves **performance**.  

---

## 🏗️ Engine Components  

| Component      | Description |
|---------------|------------|
| 🎭 **Shader**       | Manages OpenGL shader programs. |
| 🖼️ **Texture**      | Handles texture loading and binding. |
| 🎨 **Renderer**     | Optimizes sprite rendering via batching. |
| 📦 **AssetPool**    | Caches and reuses shaders, textures, and spritesheets. |
| 🎮 **GameObject**   | The base entity that holds components. |

---

## 📌 Planned Features  

🚀 **Physics Engine Integration** (Box2D)  
🎵 **Basic Audio System** for sound & music  
🖥️ **GUI System** for in-game UI elements  
💾 **Scene Serialization & Loading**  

---

## 📢 Notes  
⚠️ This project is a **work in progress** and is meant for learning. It is **not** optimized for production use.  

## 🖥️ What its currently able to display
![image](https://github.com/user-attachments/assets/b0e928f5-85ae-49e9-9f48-32761a30b923)

