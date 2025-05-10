package com.example.ProgromacionNCapasXimena.Controller;

import com.example.ProgromacionNCapasXimena.ML.Colonia;
import com.example.ProgromacionNCapasXimena.ML.Direccion;
import com.example.ProgromacionNCapasXimena.ML.Estado;
import com.example.ProgromacionNCapasXimena.ML.Municipio;
import com.example.ProgromacionNCapasXimena.ML.Pais;
import com.example.ProgromacionNCapasXimena.ML.Result;
import com.example.ProgromacionNCapasXimena.ML.ResultFile;
import com.example.ProgromacionNCapasXimena.ML.Roll;
import com.example.ProgromacionNCapasXimena.ML.Usuario;
import com.example.ProgromacionNCapasXimena.ML.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Usuario")
public class UsuarioController {
   //---------

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    //vista principal
    public String Index(Model model) {

        ResponseEntity<Result<UsuarioDireccion>> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi",
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });

        Result response = responseEntity.getBody();

        Usuario usuarioBusqueda = new Usuario();
        usuarioBusqueda.Roll = new Roll();

        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
//        model.addAttribute("roles", RollDAOImplementation.GetAll().object);
        model.addAttribute("listaUsuario", response.objects);

        return "HolaMundo";
    }

    @GetMapping("/CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {

        try {
            //guardarlo en el sistema
            if (archivo != null && !archivo.isEmpty()) {

                //body
                ByteArrayResource byteArrayResource = new ByteArrayResource(archivo.getBytes()) {
                    @Override
                    public String getFilename() {
                        return archivo.getOriginalFilename();
                    }
                };

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("archivo", byteArrayResource);

                //headers 
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                //entidad de la petición
                HttpEntity<MultiValueMap<String, Object>> httpEntity
                        = new HttpEntity<>(body, httpHeaders);

                ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/CargaMasiva",
                        HttpMethod.POST,
                        httpEntity,
                        new ParameterizedTypeReference<Map<String, Object>>() {

                });

                //lista de errores
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    model.addAttribute("bien", true);
                    session.setAttribute("urlFile", responseEntity.getBody().get("object"));
                } else {
                    if (responseEntity.getStatusCode().is4xxClientError()) {
                        model.addAttribute("listaErrores", (String) responseEntity.getBody().get("objects"));
                    }
                }
            }
        } catch (Exception ex) {
            return "redirect:/Usuario/CargaMasiva";
        }
        return "CargaMasiva";
    }

    @GetMapping("/CargaMasiva/Procesar")
    public String Procesar(HttpSession session) {
        String absolutePath = session.getAttribute("urlFile").toString();

        ResponseEntity<Result> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/CargaMasiva/Procesar",
                HttpMethod.POST,
                new HttpEntity<>(absolutePath),
                new ParameterizedTypeReference<Result>() {

        });

//        Result result = responseEntity.getBody(); 
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Todo bien");
        } else {
            if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
                System.out.println("Error del lado del cliente");
            }
        }
        return "/CargaMasiva";

    }

//    @PostMapping("/GetAllDinamico")
//    public String BusquedaDinamica(@ModelAttribute Usuario usuario, Model model) {
//        Result result = usuarioDAOImplementation.GetAllDinamico(usuario);
//        Result resultRoll = RollDAOImplementation.GetAll();
//        Usuario usuarioBusqueda = new Usuario();
//        usuarioBusqueda.Roll = new Roll();
//
//        model.addAttribute("usuarioBusqueda", usuarioBusqueda);
//        model.addAttribute("listaUsuario", result.objects);
//        model.addAttribute("roles", RollDAOImplementation.GetAll().object);
//
//        return "HolaMundo";
//    }
//
    //-----------------
    @GetMapping("Form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {
   //---------

        if (IdUsuario == 0) { //Agregar usuario
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.Roll = new Roll();
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.Colonia = new Colonia();
            usuarioDireccion.Direccion.Colonia.Municipio = new Municipio();
            usuarioDireccion.Direccion.Colonia.Municipio.Estado = new Estado();
            usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais = new Pais();

            ResponseEntity<Result<List<Roll>>> response = restTemplate.exchange("http://localhost:8081/rollapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Roll>>>() {
            });

            ResponseEntity<Result<List<Pais>>> responsePais = restTemplate.exchange("http://localhost:8081/paisapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Pais>>>() {
            });

            model.addAttribute("roles", response.getBody().object);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            //Solo se coloca Pais ya que no se deben rellenar todos los otros campos
            model.addAttribute("paises", responsePais.getBody().object);

            return "Formulario";
   //---------

        } else { //Editar

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

            ResponseEntity<Result<Usuario>> responseUsuario = restTemplate.exchange("http://localhost:8081/usuarioapi/GetByIdJPA?IdUsuario=" + IdUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            }
            );

            //usuarioDireccion.Usuario = (responseUsuario.getStatusCode() == HttpStatus.OK) ? responseUsuario.getBody().object : null;
            usuarioDireccion.Usuario = responseUsuario.getBody().object;

            ResponseEntity<Result<List<Direccion>>> responseDirecciones = restTemplate.exchange("http://localhost:8081/usuarioapi/GetDireccionById?IdUsuario=" + IdUsuario,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Direccion>>>() {
            });

            usuarioDireccion.Direcciones = responseDirecciones.getBody().object;

            model.addAttribute("usuarioDirecciones", usuarioDireccion);

            return "ViewUsuarioDireccion";
        }
    }

    //----------------------
    //RELLENA LOS CAMPOS AL MOMENTO DE EDITAR UN USUARIO O DIRECCION
    @GetMapping("/FormularioEdit")
    public String FormularioEditar(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
   
    //---------

        if (IdDireccion == null) { //Editar Usuario
            Result result = new Result();
            try {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Usuario.Roll = new Roll();

                ResponseEntity<Result<Usuario>> responseEntityUsuario = restTemplate.exchange("http://localhost:8081/usuarioapi/GetByIdJPA?IdUsuario=" + IdUsuario,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<Usuario>>() {
                });

                //extrae los datos de usuario de una solicitud HTTP y los guarda en la instancia de la clase.
                usuarioDireccion.Usuario = (Usuario) responseEntityUsuario.getBody().object;

                ResponseEntity<Result<List<Roll>>> responseEntityRoll = restTemplate.exchange("http://localhost:8081/rollapi",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<List<Roll>>>() {

                });

                //usuarioDireccion.Usuario.Roll = responseEntityRoll.getBody().object;
                //sirve para que se muestre el formulario solo la parte del Usuario
                usuarioDireccion.Direccion.setIdDireccion((-1));

                model.addAttribute("usuarioDireccion", usuarioDireccion);
//            model.addAttribute("usuarios", responseEntityUsuario.getBody().object);
                model.addAttribute("roles", responseEntityRoll.getBody().object);
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;

            }

        } else if (IdDireccion == 0) { //Sirve para agregar Direccion
   //---------
            Result result = new Result();
            try {
                
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setIdDireccion(0);

                ResponseEntity<Result<List<Pais>>> responseEntityPais = restTemplate.exchange("http://localhost:8081/paisapi",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<Result<List<Pais>>>() {

                });

                model.addAttribute("usuarioDireccion", usuarioDireccion);
                model.addAttribute("paises", responseEntityPais.getBody().correct ? responseEntityPais.getBody().object : null);

            } catch (Exception e) {
                result.correct = false;
                result.errorMessage = e.getLocalizedMessage();
                result.ex = e;
            }

        //Agregar municipio, estado, 
        } else { //Sirve para editar dirección 
            Result result = new Result(); 
            try {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
          

            ResponseEntity<Result<Direccion>> responseEntityDireccion = restTemplate.exchange("http://localhost:8081//usuarioapi/GetByIdDireccionJPA?IdDireccion=" + IdDireccion,
                    HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Direccion>>() {

            });
            
            usuarioDireccion.Direccion = (Direccion) responseEntityDireccion.getBody().object;

            ResponseEntity<Result<List<Pais>>> responseEntityPais = restTemplate.exchange("http://localhost:8081/paisapi",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<List<Pais>>>() {

            });

            ResponseEntity<Result<Estado>> responseEntityEstado = restTemplate.exchange("http://localhost:8081/estadoapi/EstadoByIdPais/" + usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Estado>>() {

            });

            ResponseEntity<Result<Municipio>> responseEntityMunicipio = restTemplate.exchange("http://localhost:8081/municipioapi/MunicipioByIdEstado/" + usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Municipio>>() {

            });

            ResponseEntity<Result<Colonia>> responseEntityColonia = restTemplate.exchange("http://localhost:8081/coloniaapi/ColoniaByIdMunicipio/" + usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Colonia>>() {

            });

            
            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.Direccion = responseEntityDireccion.getBody().object;
                   
            //recuperar direcciones, colonias, municipios, estados, paises al momento de que el usuario quiera editar una direccion
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", responseEntityPais.getBody().correct ? responseEntityPais.getBody().object : null);
            model.addAttribute("estados", responseEntityEstado.getBody().objects);
            model.addAttribute("municipios", responseEntityMunicipio.getBody().objects);
            model.addAttribute("colonias", responseEntityColonia.getBody().objects);

                
            } catch (Exception ex) {
                result.correct = false; 
                result.errorMessage = ex.getLocalizedMessage(); 
                result.ex = ex;
            }
            //Direccion
        }
        return "Formulario";
    }

    @PostMapping("Form")
    //agregar, actualizar
    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindingResult, @RequestParam(required = false) MultipartFile imagenFile, Model model) {

        //RestTemplate ----> herramienta para interactuar con servicios web RESTful(API)
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<UsuarioDireccion> entity = new HttpEntity<>(usuarioDireccion);

        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.Usuario.setImagen(imgBase64);
            }

        } catch (Exception Ex) {

        }

        if (usuarioDireccion.Usuario.getIdUsuario() == 0) {

            ResponseEntity<Result<UsuarioDireccion>> responseEntityAdd = restTemplate.exchange("http://localhost:8081/usuarioapi/AddJPA",
                    HttpMethod.POST, entity, new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
            });

            //        sirve para extraer el cuerpo (o contenido) de una respuesta HTTP que ha sido recibida, almacenada en la variable responseEntity
            Result response = responseEntityAdd.getBody();

            System.out.println("Agregando nuevo usuario y direccion");

        } else {
            if (usuarioDireccion.Direccion.getIdDireccion() == -1) {

                ResponseEntity<Result<UsuarioDireccion>> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/UpdateUsuarioJPA",
                        HttpMethod.PUT, entity, new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
                });
                Result response = responseEntity.getBody();

                System.out.println("Actualizando usuario");

            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) {

                System.out.println("Agregar Direccion");

            } else {
                System.out.println("actualizando direccion");
            }
        }
        return "redirect:/Usuario";
    }

    //    ----------
    //eliminar usuario desde la tabla de usuario
    @GetMapping("/UsuarioDelete")
    public String UsuarioDelete(@RequestParam int IdUsuario) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Result> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/DeleteJPA?IdUsuario=" + IdUsuario,
                    HttpMethod.DELETE, HttpEntity.EMPTY, new ParameterizedTypeReference<Result>() {
            });

            Result result = responseEntity.getBody();

        } catch (Exception Ex) {
            System.out.println(Ex.getLocalizedMessage());
        }

        return "redirect:/Usuario";
    }

    //eliminar direccion desde la tabla de detalles de direcciones
    @GetMapping("/DireccionDelete")
    public String DireccionDelete(@RequestParam int IdDireccion) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            //se coloca el result vacio ya que no va a mandar ni va a recibir nada
            ResponseEntity<Result> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/DeleteDireccionJPA?IdDireccion=" + IdDireccion,
                    HttpMethod.DELETE, HttpEntity.EMPTY, new ParameterizedTypeReference<Result>() {

            });

            Result result = responseEntity.getBody();

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return "redirect:/Usuario";
    }
}
