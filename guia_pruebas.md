# Guía de pruebas — APF3 (persistencia, CRUD y validación)

Antes de probar: tener la base lista (local o en la nube) con `docs/script_bd.sql` ejecutado y las variables `DB_URL`, `DB_USER`, `DB_PASSWORD` definidas (ver `docs/guia_nube.md`).
Con la aplicación en ejecución (`http://localhost:8080`). Tomar una captura de cada prueba para el informe.

## Persistencia (lo que pide la clase: "los datos permanecen después de reiniciar")
1. Registrar un producto desde `/admin/productos/nuevo`.
2. Detener y volver a ejecutar Spring Boot.
3. Entrar a `/productos` o `/admin/productos`: el producto sigue ahí.
4. Mostrar en pgAdmin: `SELECT * FROM productos;`

## CRUD con Thymeleaf
| N.º | URL / acción | Resultado esperado |
|---|---|---|
| 1 | `/productos` | Menú con las tarjetas leídas de la base de datos |
| 2 | `/productos?nombre=la` | Solo productos cuyo nombre contiene "la" |
| 3 | Menú → filtro "Postres" → Buscar | Solo Cheesecake y Brownie |
| 4 | `/admin/productos` | Tabla con todos los productos (categoría y proveedor visibles) |
| 5 | Nuevo producto con datos válidos | Redirige a la tabla con "Producto registrado correctamente." |
| 6 | Editar un producto y cambiar el precio | "Producto actualizado correctamente." |
| 7 | Eliminar un producto | "Producto eliminado correctamente." |
| 8 | `/admin/categorias` y `/admin/proveedores` | Registrar, editar y eliminar funcionan igual |
| 9 | Eliminar una categoría que tiene productos | Mensaje rojo: no se puede eliminar porque tiene productos asociados |

## Validación (Spring Validator)
Los formularios tienen validación HTML (`required`, `minlength`, `min`). Para ver los mensajes del **servidor**, escribir solo espacios en un campo de texto (pasa el HTML pero falla `@NotBlank`), dejar sin elegir la categoría/proveedor, o usar Postman.

| N.º | Prueba | Mensaje esperado |
|---|---|---|
| 1 | Nombre con solo espacios | El nombre es obligatorio |
| 2 | Categoría sin seleccionar ("Seleccione...") | La categoría es obligatoria |
| 3 | Proveedor sin seleccionar | El proveedor es obligatorio |
| 4 | Precio 0 o negativo (por Postman) | El precio debe ser mayor que cero |
| 5 | Stock negativo (por Postman) | El stock no puede ser negativo |

## API REST (Postman)
- `GET /api/productos` → 200 con JSON (incluye `categoria` y `proveedor`)
- `GET /api/productos/1` → 200; `GET /api/productos/9999` → 404
- `GET /api/productos/buscar?categoria=Postres` → JSON de los productos de esa categoría

**POST** `/api/productos` → **201 Created**
```json
{
  "nombre": "Té Matcha Latte",
  "descripcion": "Té matcha con leche vaporizada.",
  "precio": 13.5,
  "stock": 7,
  "imagen": "te.jpg",
  "categoria": { "id": 2 },
  "proveedor": { "id": 2 }
}
```

**POST** inválido → **400 Bad Request** con los errores por campo
```json
{
  "nombre": "AB",
  "descripcion": "Prueba",
  "precio": -5,
  "stock": 3,
  "imagen": "",
  "categoria": { "id": 2 },
  "proveedor": { "id": 2 }
}
```
Respuesta esperada (los textos salen de las anotaciones del modelo):
```json
{
  "nombre": "El nombre debe tener entre 3 y 80 caracteres",
  "precio": "El precio debe ser mayor que cero",
  "imagen": "La imagen es obligatoria"
}
```

**PUT** `/api/productos/1` → 200 con el mismo formato de cuerpo que el POST; **DELETE** `/api/productos/{id}` → 200 "Producto eliminado correctamente".
