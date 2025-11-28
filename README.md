
# Txurdiliga

Aplicación de gestión de ligas deportivas (Java Swing).

## Descripción

Txurdiliga es una aplicación de escritorio para gestionar ligas organizadas en temporadas. Permite crear y consultar jornadas, administrar equipos y jugadores, y controlar accesos mediante roles de usuario.

## Características principales

- **Gestión de jornadas:** Ver y crear jornadas y sus partidos.
- **Administración de equipos:** Alta, edición y baja de equipos.
- **Administración de jugadores:** Asociar jugadores a equipos y gestionar sus datos.
- **Gestión de temporadas:** Mantener ligas separadas por temporadas.
- **Sistema de usuarios y roles:** Control de acceso por roles (administrador, árbitro, usuario, invitado).
- **Serialización:** Persistencia simple mediante ficheros `.ser` presentes en el proyecto.

## Estructura del proyecto (resumen)

- **`src/`**: Código fuente Java.
	- **`src/clases/`**: Clases de modelo y lógica: `Equipo.java`, `Gestion.java`, `Jornada.java`, `Jugador.java`, `log.java`, `Partido.java`, `Temporada.java`, `usuario.java`, `Xml.java`.
	- **`src/ventanas/`**: Clases de interfaz (ventanas Swing): `añadirUsuario.java`, `EquiposWindow.java`, `gestionAdmin.java`, `JornadasWindow.java`, `JugadoresWindow.java`, `LoginWindow.java`, `TemporadasWindow.java`, `UsuariosWindow.java`.
- **`bin/`**: Contiene clases compiladas y documentación generada (javadoc) en `bin/clases/`.
- **`doc/`**: Documentación del proyecto (ficheros HTML de javadoc y recursos estáticos).
- **`lib/`**: Dependencias externas (si aplica).
- **Ficheros de datos:** `temporadas.ser`, `usuarios.ser` — persistencia por serialización.

## Requisitos

- Java JDK 8 o superior.
- (Opcional) IDE: IntelliJ IDEA, Eclipse o NetBeans para abrir y ejecutar el proyecto con facilidad.

## Compilar y ejecutar (PowerShell)

- Compilar desde la raíz del proyecto:

```powershell
cd src
javac -d ../bin clases/*.java ventanas/*.java
```

- Ejecutar (la clase principal de entrada típica es la ventana de login; si cambia, sustituir la clase principal):

```powershell
cd ..
java -cp bin ventanas.LoginWindow
```

Nota: Si usas un IDE, importa el directorio `src/` como proyecto Java y ejecuta la clase `ventanas.LoginWindow` o la que contenga el método `main`.

## Generar JAR (opcional)

```powershell
cd bin
jar cfe Txurdiliga.jar ventanas.LoginWindow -C . .
```

Luego ejecutar:

```powershell
java -jar Txurdiliga.jar
```

## Usuarios por defecto y roles

La aplicación incluye usuarios de ejemplo para pruebas:

- `Itxiar` / `pass123` — Administrador (rol 1)
- `Arnaitz` / `pass456` — Administrador (rol 1)
- `Andres` / `pass789` — Administrador (rol 1)
- `Arbitro` / `pass799` — Árbitro (rol 2)
- `Usuario` / `pass759` — Usuario (rol 3)
- `Invitado` / `pass739` — Invitado (rol 4)

Usa `usuarios.ser` para restaurar la lista si necesitas reiniciar los usuarios de prueba.

## Desarrollo y estilo

- Sigue la convención de nombres Java y organiza la lógica en `clases/` y la UI en `ventanas/`.
- Añade tests/unitarios si se integra un framework de pruebas (por ejemplo, JUnit) en futuras versiones.

## Contribuir

- Abre un issue describiendo el bug o la mejora.
- Para cambios de código: crea una rama, implementa la mejora y abre un pull request.
- Mantén commits claros y atómicos.

## Mejoras sugeridas

- Integración con base de datos (SQLite/H2/Postgres) para persistencia robusta.
- Internacionalización (soporte multi-idioma).
- Tests automáticos y CI.
- Interfaz gráfica modernizada (JavaFX o migración a web).
- Exportación de datos (CSV/Excel) y generación de estadísticas.

## Recursos y referencias

- Documentación Javadoc generada en `doc/` y `bin/clases/`.

## Autor y contacto

Proyecto: `Txurdiliga` — Autor: `andressito99`.
Para dudas o colaboración, abre un issue en el repositorio o contacta al mantenedor.

---

_README actualizado automáticamente para ser más claro y útil. Si quieres, puedo añadir ejemplos de uso concretos (capturas, flujos de usuario) o generar un script de build._

