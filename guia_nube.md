# Guía: base de datos PostgreSQL en la nube (ejemplo con Neon)

1. Crear cuenta en https://neon.tech y un proyecto nuevo (nombre `alba-coffi`, región más cercana).
2. En el panel, copiar los datos de conexión: host, base (`neondb`), usuario y contraseña.
3. Abrir **SQL Editor**, pegar todo `docs/script_bd.sql` y ejecutar. Verificar con `SELECT * FROM productos;`.
4. Armar la URL JDBC (importante: formato JDBC, no `postgresql://`):
   `jdbc:postgresql://<HOST>/<BASE>?sslmode=require`
5. Definir las variables de entorno antes de ejecutar la app.

   Windows (PowerShell):
   ```
   $env:DB_URL="jdbc:postgresql://<HOST>/<BASE>?sslmode=require"
   $env:DB_USER="<USUARIO>"
   $env:DB_PASSWORD="<CONTRASEÑA>"
   .\mvnw.cmd spring-boot:run
   ```
   Linux/Mac:
   ```
   export DB_URL="jdbc:postgresql://<HOST>/<BASE>?sslmode=require"
   export DB_USER="<USUARIO>"; export DB_PASSWORD="<CONTRASEÑA>"
   ./mvnw spring-boot:run
   ```
6. Abrir http://localhost:8080/productos: los productos vienen de la nube. Registrar uno, reiniciar la app y comprobar que sigue ahí; mostrarlo también en el SQL Editor (captura para el informe).

Notas: en el plan gratuito la base puede "dormirse"; abrir el panel de Neon antes de la demo para despertarla. Nunca subir la contraseña a GitHub. Si usan Supabase u otro proveedor, solo cambia la URL/usuario/contraseña.
