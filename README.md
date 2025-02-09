# Diccionario Cliente-Servidor con Server Listener

## Descripción
Este proyecto implementa una aplicación de diccionario basada en un modelo cliente-servidor. Permite a los clientes conectarse a un servidor, buscar palabras en un diccionario, agregar nuevas palabras y recibir mensajes en tiempo real mediante un `ServerListener` en el cliente.

## Estructura del Proyecto

```
- client/
  - ClientApp.java
  - threads/
    - ServerListener.java
- server/
  - ServerApp.java
  - threads/
    - DictionaryClientHandler.java
- utils/
  - Constanst.java
```

## Componentes

### Cliente (`ClientApp`)
El cliente permite a un usuario conectarse al servidor, enviar solicitudes y recibir respuestas. Se integra un `ServerListener` para manejar mensajes entrantes de forma asíncrona.

#### Manejo Clave:
- **Se agregó `ServerListener`**: Un hilo que escucha los mensajes del servidor.
- **Se eliminó la espera de respuestas en el `while` principal**, ya que `ServerListener` se encarga de imprimir las respuestas.

### Server Listener (`ServerListener`)
Clase que corre en un hilo separado y recibe mensajes del servidor de manera asíncrona.

```java
public class ServerListener extends Thread {
  private DataInputStream inputStream;

  public ServerListener(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Override
  public void run() {
    while (true) {
      try {
        String serverMessage = inputStream.readUTF();
        System.out.println(serverMessage);
      } catch (IOException e) {
        System.out.println("Problema recibiendo mensaje.");
        break;
      }
    }
  }
}
```

### Servidor (`ServerApp`)
El servidor acepta conexiones de clientes y asigna un `DictionaryClientHandler` para manejar cada uno de ellos.

### Manejador de Clientes (`DictionaryClientHandler`)
Cada cliente tiene su propio hilo `DictionaryClientHandler`, que gestiona las solicitudes del usuario y envía respuestas apropiadas.

## Uso
1. **Iniciar el servidor:**
   ```sh
   java server.ServerApp
   ```
2. **Iniciar el/los cliente:**
   ```sh
   java client.ClientApp
   ```
3. **Opciones disponibles en el cliente:**
   - `1` Buscar una palabra en el diccionario.
   - `2` Agregar una nueva palabra con su significado.
   - `3` Salir del diccionario.

## Requerimientos
- Java 8 o superior
- Puerto definido en `Constanst.SERVER_PORT` disponible

## Conclusión
Este sistema mejora la comunicación en tiempo real con `ServerListener`, permitiendo que el cliente reciba mensajes de manera asíncrona sin bloquear su ejecución principal.

