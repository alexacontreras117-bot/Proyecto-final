# Sistema de Gestion de Productos de Almacen

Aplicacion de escritorio en Java (Swing + JDBC) que implementa el mandato del
proyecto final: Login/Registro de usuarios y CRUD de productos, conectada a
una base de datos MySQL llamada `almacenitlafinal`.

## Estructura del proyecto (arquitectura por capas)

```
com.almacen
├── Main.java                  -> Punto de entrada
├── model/                     -> Entidades (Persona, Usuario, Producto)
├── db/ConexionBD.java         -> Conexión a MySQL (patrón Singleton)
├── dao/                       -> Acceso a datos (patrón DAO + Factory)
└── view/                      -> Pantallas Swing (Login, Registro, Principal,
                                   Gestión de Usuarios, Gestión de Productos)
```

### Pilares de POO aplicados (comentados en el codigo)
- **Abstraccion**: clase abstracta `Persona`, interfaces `UsuarioDAO` / `ProductoDAO`.
- **Encapsulamiento**: atributos privados con getters/setters en todos los modelos.
- **Herencia**: `Usuario extends Persona`.
- **Polimorfismo**: `descripcion()` sobrescrito en `Usuario`; las implementaciones
  `UsuarioDAOImpl`/`ProductoDAOImpl` se usan a través de sus interfaces.

### Patrones de diseño aplicados (comentados en el codigo)
- **Singleton** → `ConexionBD` (una sola conexión reutilizada en toda la app).
- **DAO (Data Access Object)** → separa el acceso a datos de la logica/vistas.
- **Factory** → `DAOFactory` centraliza la creación de los objetos DAO.

## 1. Configurar la conexion a la base de datos

El archivo `src/main/java/com/almacen/db/ConexionBD.java` esta configurado
para conectarse a tu **base de datos local** (la que creaste con MySQL
Workbench, ejecutando `almacenitlafinal.sql`). Solo debes abrir ese archivo
y colocar tu password de MySQL en la constante `PASSWORD`:



> Requisito: debes tener MySQL corriendo en tu computadora (localhost:3306)
> con la base de datos `almacenitlafinal` y sus tablas `productos` y
> `usuarios` ya creadas (el script `almacenitlafinal.sql` las crea).

## 2. Como abrir y ejecutar el proyecto

### Opcion A: Eclipse / IntelliJ (como proyecto Maven)
1. Abre Eclipse o IntelliJ → **File > Open/Import** → selecciona la carpeta
   `almacen-app` (la que contiene `pom.xml`). El IDE detectara que es un
   proyecto Maven y descargara automaticamente el driver de MySQL.
2. Espera a que termine de descargar las dependencias (barra de progreso).
3. Ejecuta la clase `com.almacen.Main` con clic derecho > **Run As > Java Application**.

### Opcion B: Linea de comandos con Maven
```bash
cd almacen-app
mvn clean package
mvn exec:java
```
o, tras `mvn clean package`, ejecutar el jar generado (incluye el driver):
```bash
java -jar target/almacen-app-1.0.0.jar
```

## 3. Flujo de la aplicacion (segun el mandato)
1. **Login**: ingresa usuario/contraseña o haz clic en "Registrarse".
2. **Registro**: completa todos los campos (la contraseña se oculta con `JPasswordField`).
3. Tras iniciar sesion aparece la **pantalla principal** con los botones
   **Usuarios** y **Productos**, y el boton **Cerrar Sesion**.
4. **Gestion de Usuarios**: lista todos los usuarios; permite Nuevo, Actualizar
   y Eliminar.
5. **Gestion de Productos**: lista todos los productos; botón **Nuevo** abre el
   formulario de registro; doble clic en un producto abre su ficha con
   **Guardar** y **Eliminar**.

