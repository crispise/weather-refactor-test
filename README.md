# Preguntas

- **¿Qué has empezado implementando y por qué?**  
  Lo primero que hice al ver la prueba fue analizar el código para entender qué funcionalidades tenía. Además, dediqué un tiempo a revisar la documentación de la API de Open-Meteo para comprobar qué información ofrecía y cómo se estructuraban las peticiones y respuestas.  
  Cuando tuve claro el objetivo, decidí utilizar Spring Boot. Aunque en este caso no era necesario, consideré que aportaba varias ventajas como facilitar la configuración del proyecto, simplificar la realización de peticiones a la API mediante Spring Web y exponer un endpoint propio que resulta útil tanto para realizar pruebas reales con Postman como para una futura integración con otros sistemas.  
  Una vez creado el proyecto, definí los *records* necesarios, tanto para gestionar las peticiones de entrada como para mapear las respuestas devueltas por las API.

---

- **¿Qué problemas te has encontrado al implementar los tests y cómo los has solventado?**  
  Como no tengo tanta experiencia creando tests, esta parte fue la que más se me ha resistido. Al principio me resultó complicado configurar bien el entorno y entender cómo simular las dependencias. Por ejemplo, con @WebMvcTest tuve que investigar cómo usar @MockitoBean para poder aislar el servicio y ejecutar los tests sin necesidad de levantar todo el contexto.

  Otro reto fue cómo probar todos los casos del switch que traduce los códigos meteorológicos. Inicialmente pensé en crear un test por cada código, lo que era repetitivo y difícil de mantener. La solución fue utilizar un @ParameterizedTest con @CsvSource, que me permitió definir en una sola tabla todos los códigos y las descripciones esperadas, ejecutando el mismo test para cada caso.

  En general, aunque los tests ha sido lo que más se me ha resistido, también ha sido una parte que me ha permitido aprender más para poder aplicarlos en futuros proyectos.

---

- **¿Qué componentes has creado y por qué?**  
  He creado cuatro componentes principales:
    - OpenMeteoClient que se encarga de construir la URL y realizar la llamada a la API externa. Lo hice así para centralizar toda la lógica relacionada con la comunicación con Open-Meteo y mantener el resto del código desacoplado por si en un futuro se quisiera cambiar la API de Open-Meteo por otra.
    - WeatherCodeTranslatorService que traduce los códigos WMO en descripciones. Lo creé para encapsular la lógica del switch y poder reutilizarlo fácilmente en distintos puntos de la aplicación si hiciera falta.
    - WeatherInfoService, este servicio utiliza al cliente externo y al traductor, añade la descripción al resultado y controla casos de error. Es la pieza que hace de puente entre la API externa y nuestro endpoint.
    - WeatherController, que expone un endpoint REST. Aunque no era estrictamente necesario, lo consideré útil porque me permitía probar la aplicación con Postman de una forma más realista y, además, deja preparada la base para una futura integración con otros sistemas.

---

- **Si has utilizado dependencias externas, ¿por qué has escogido esas dependencias?**  
  En cuanto a dependencias externas, utilicé principalmente los starters de Spring Boot: spring-boot-starter-web, porque me permitía construir fácilmente las peticiones HTTP a la API de Open-Meteo y convertir automáticamente las respuestas JSON en objetos Java con Jackson, además de exponer un endpoint REST para poder probar la aplicación con Postman; spring-boot-starter-validation, para validar de forma sencilla los datos de entrada en los records; y spring-boot-starter-cache junto con Caffeine, que ofrece una caché en memoria muy eficiente y se integra perfectamente con Spring para evitar llamadas repetidas a la API externa. También usé Lombok para reducir código repetitivo (getters, setters) y, en la parte de pruebas, combiné Spring Boot Starter Test con JUnit 5 y Mockito, lo que me permitió escribir tests unitarios más claros y simular dependencias sin necesidad de acceder a la API real.

---

- **¿Has utilizado streams, lambdas y optionals de Java 8 o superior? ¿Qué te parece la programación funcional?**  
  Sí, he utilizado streams y lambdas. Por ejemplo, en el manejador global de excepciones usé .stream() junto con .map() para procesar los errores de validación y devolver una respuesta más legible al cliente. En cuanto a la programación funcional, me parece muy útil. Al principio cuesta un poco cambiar la forma de pensar y tiene cierta curva de aprendizaje, pero una vez te acostumbras ayuda a escribir un código más limpio y fácil de mantener.

---

- **¿Qué piensas del rendimiento de la aplicación?**  
  Creo que para lo que hace la aplicación el rendimiento es bueno, la lógica interna es sencilla y lo que más puede costar son las llamadas a la api externa y para eso he añadido la caché de Caffeine.

---

- **¿Qué harías para mejorar el rendimiento si esta aplicación fuera a recibir al menos 100 peticiones por segundo?**  
  Además de la caché que ya tengo, algo sencillo sería poner un límite básico de llamadas a la API externa con un contador y no dejar que se hagan más de X peticiones por segundo.

---

- **¿Cuánto tiempo has invertido para implementar la solución?**  
  He invertido unas 10-12 horas en total, repartidas en varios días. No solo fue implementar la lógica, sino también entender bien el código que se me daba, investigar la API de Open-Meteo, configurar Spring Boot y dedicar tiempo a aprender y ajustar los tests, que era la parte donde tenía menos experiencia.

---

- **¿Crees que en un escenario real valdría la pena dedicar tiempo a realizar esta refactorización?**  
  Sí, siempre vale la pena refactorizar y separar responsabilidades, no solo porque el código queda más claro y entendible, sino también porque a largo plazo es mucho más fácil de mantener y de ampliar.  
