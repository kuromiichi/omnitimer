# App TFG

## Vista de cronómetro

### Requisitos funcionales

- RF-01: Cronometrar un tiempo
- RF-02: Generar mezclas
- RF-03: Aplicar penalizaciones
- RF-04: Anular un tiempo
- RF-05: Mostrar imagen de la mezcla
- RF-06: Mostrar estadísticas sencillas de sesión actual
- RF-07: Seleccionar categoría
- RF-08.1: Seleccionar subcategoría
- RF-08.2: Crear subcategoría
- RF-08.3: Modificar subcategoría
- RF-08.4: Eliminar subcategoría

### Requisitos no funcionales

- RNF-01: El tiempo debe iniciarse cuando el usuario pulsa la pantalla o botón, y detenerse cuando se vuelva a pulsar
- RNF-02: Se debe seguir un tiempo de inspección definido en los ajustes de la app
- RNF-03.1: Se debe generar una mezcla para cada tiempo en función de la categoría seleccionada
- RNF-03.2: El usuario puede generar nuevas mezclas para el tiempo actual mediante un botón
- RNF-04: Tras terminar un tiempo, se debe poder aplicar penalizaciones o eliminar el tiempo
- ~~RNF-05: La imagen de la mezcla debe seguir el esquema de colores definido por el usuario~~


### Requisitos de información

- RI-01: El tiempo debe almacenar la mezcla asociada
- RI-02: El tiempo debe registrar penalizaciones
- RI-03: Las estadísticas deben utilizar los últimos tiempos registrados en la sesión actual

## Lista de tiempos

### Requisitos funcionales

- RF-09: Mostrar listado de tiempos en sesión actual
- RF-10: Mostrar detalle de un tiempo
- RF-11: Editar penalizaciones de un tiempo
- RF-12: Eliminar un tiempo
- RF-13: Archivar tiempos
- RF-14: Mostrar tiempos archivados
- RF-15: Visualizar tiempos 

### Requisitos no funcionales

- RNF-06: Se debe mostrar el detalle de un tiempo pulsando en el mismo
- RNF-07: El detalle debe mostrar la mezcla asociada, los penalizadores y la fecha de registro del tiempo
- RNF-08: Se debe poder archivar los tiempos de la sesión actual, creando una sesión nueva
- RNF-09: Se debe mostrar los tiempos archivados con un botón
- RNF-10: Se debe poder eliminar un tiempo manteniendo pulsado en él y después confirmando en un cuadro de diálogo
- RNF-11: Se deben mostrar los tiempos en función de la categoría y subcategoría seleccionadas

### Requisitos de información

- RI-04: El tiempo debe almacenar la mezcla asociada
- RI-05: El tiempo debe registrar penalizaciones
- RI-06: El tiempo debe ser activo o archivado
- RI-07: El tiempo debe registrar la imagen de la mezcla

## Ajustes de la app

### Requisitos funcionales

- RF-16: Registrar usuario
- RF-17: Iniciar sesión
- RF-18: Cerrar sesión
- RF-19: Ajustar tiempo de inspección
- RF-20: Ajustar aviso de inspección
- RF-21: Seleccionar estadísticas visibles
- RF-22: Eliminar registro de tiempos

### Requisitos no funcionales

- RNF-12: El usuario debe estar registrado antes de iniciar o cerrar sesión
- RNF-13: El tiempo de inspección debe ser de 0 segundos (desactivado), o 15 segundos (activado)
- RNF-14: No se puede modificar el aviso de inspección si el tiempo de inspección está desactivado
- RNF-15: El aviso de inspección debe activarse a los 8 y 12 segundos si tanto el aviso como la inspección están habilitados
- RNF-16: Eliminar el registro de tiempos debe borrar todos los tiempos de todas las categorías y sesiones

### Requisitos de información

- RI-08: Se debe guardar el estado de la inspección
- RI-09: Se debe guardar el estado del aviso de inspección
- RI-10: Se debe guardar la lista de estadísticas visibles
