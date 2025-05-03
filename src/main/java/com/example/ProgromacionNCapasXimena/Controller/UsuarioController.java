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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping
    //vista principal
    public String Index(Model model) {

        RestTemplate restTemplate = new RestTemplate();

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
//
//    @GetMapping("/CargaMasiva")
//    public String CargaMasiva() {
//        return "CargaMasiva";
//    }
//
//    @PostMapping("/CargaMasiva")
//    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
//        try {
//
//            if (archivo != null && !archivo.isEmpty()) { //Sirve para que el archivo no este nulo ni esté vacio
//
//                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];
//
//                String root = System.getProperty("user.dir"); //Obtiene la direccion del proyecto de la computadora
//                String path = "src/main/resources/static/archivos"; //Guarda internamente la direccion del proyecto
//                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
//                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
//                //se guarda en AbsolutePath
//                archivo.transferTo(new File(absolutePath));
//
//                //Leer archivo 
//                List<UsuarioDireccion> listaUsuarios = new ArrayList();
//                if (tipoArchivo.equals("txt")) {
//                    listaUsuarios = LecturaArchivoTXT(new File(absolutePath)); //Metodo que sirve para ller la lista                    
//                } else {
//                    listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
//                }
//
//                //Validar Archivo 
//                List<ResultFile> listaErrores = ValidarArchivo(listaUsuarios);
//
//                if (listaErrores.isEmpty()) {
//                    //Procesar archivo
//                    session.setAttribute("urlFile", absolutePath);
//                    session.setAttribute("tipoArchivo", tipoArchivo);
//                    model.addAttribute("listaErrores", listaErrores);
//                } else {
//                    //Se muestra tabla de errores 
//                    model.addAttribute("listaErrores", listaErrores);
//                }
//            }
//
//        } catch (Exception Ex) {
//            return "redirect:/Usuario/CargaMasiva";
//        }
//        return "CargaMasiva";
//    }
//
//    public String Procesar(HttpSession session) {
//        String absolutePath = session.getAttribute("urlFile").toString();
//        String tipoArchivo = session.getAttribute("tipoArchivo").toString();
//        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
//
//        if (tipoArchivo.equals("txt")) {
//            listaUsuarios = LecturaArchivoTXT(new File(absolutePath));
//        } else {
//            listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
//        }
//
//        for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
//            usuarioDAOImplementation.Add(usuarioDireccion);
//        }
//        return "/CargaMasiva";
//
//    }
//
//    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
//        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
//        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {
//
//            String linea;
//
//            while ((linea = bufferedReader.readLine()) != null) {
//                String[] campos = linea.split("\\|");
//
//                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//                usuarioDireccion.Usuario = new Usuario();
//                usuarioDireccion.Usuario.setNombre(campos[0]);
//                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
//                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
//                //Dar formato a la fecha
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
//                usuarioDireccion.Usuario.setFechaNacimiento(formatter.parse(campos[3]));
//                usuarioDireccion.Usuario.setUserName(campos[4]);
//                usuarioDireccion.Usuario.setEmail(campos[5]);
//                usuarioDireccion.Usuario.setPassword(campos[6]);
//                usuarioDireccion.Usuario.setSexo(campos[7]);
//                usuarioDireccion.Usuario.setTelefono(campos[8]);
//                usuarioDireccion.Usuario.setCelular(campos[9]);
//                usuarioDireccion.Usuario.setCURP(campos[10]);
//                usuarioDireccion.Usuario.setStatus(Integer.parseInt(campos[11]));
//
//                usuarioDireccion.Usuario.Roll = new Roll();
//                usuarioDireccion.Usuario.Roll.setIdRoll(Integer.parseInt(campos[12]));
//                usuarioDireccion.Usuario.Roll.setNombre(campos[13]);
//
//                usuarioDireccion.Direccion = new Direccion();
//                usuarioDireccion.Direccion.setCalle(campos[14]);
//                usuarioDireccion.Direccion.setNumeroInterior(campos[15]);
//                usuarioDireccion.Direccion.setNumeroExterior(campos[16]);
//
//                usuarioDireccion.Direccion.Colonia = new Colonia();
//                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[17]));
//
//                listaUsuarios.add(usuarioDireccion);
//            }
//
//        } catch (Exception Ex) {
//            listaUsuarios = null;
//        }
//        return listaUsuarios;
//    }
//
//    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
//        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
//        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
//            for (Sheet sheet : workbook) {
//                for (Row row : sheet) {
//                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//                    usuarioDireccion.Usuario = new Usuario();
//                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
//                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
//                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    usuarioDireccion.Usuario.setFechaNacimiento(simpleDateFormat.parse(simpleDateFormat.format(row.getCell(3).getDateCellValue())));
//                    usuarioDireccion.Usuario.setUserName(row.getCell(4).toString());
//                    usuarioDireccion.Usuario.setEmail(row.getCell(5).toString());
//                    usuarioDireccion.Usuario.setPassword(row.getCell(6).toString());
//                    usuarioDireccion.Usuario.setSexo(row.getCell(7).toString());
//                    usuarioDireccion.Usuario.setTelefono(row.getCell(8).toString());
//                    usuarioDireccion.Usuario.setCelular(row.getCell(9).toString());
//                    usuarioDireccion.Usuario.setCURP(row.getCell(10).toString());
//                    usuarioDireccion.Usuario.setStatus(row.getCell(11) != null ? (int) row.getCell(11).getNumericCellValue() : 0);
//                    usuarioDireccion.Usuario.Roll = new Roll();
//                    usuarioDireccion.Usuario.Roll.setIdRoll((int) row.getCell(12).getNumericCellValue());
//
//                    usuarioDireccion.Direccion = new Direccion();
//                    usuarioDireccion.Direccion.setCalle(row.getCell(13).toString());
//                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14).toString());
//                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(15).toString());
//
//                    usuarioDireccion.Direccion.Colonia = new Colonia();
//                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(16).getNumericCellValue());
//
//                    listaUsuarios.add(usuarioDireccion);
//                }
//            }
//        } catch (Exception Ex) {
//            System.out.println("Error al abrir archivo");
//        }
//
//        return listaUsuarios;
//    }
//
//    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {
//
//        List<ResultFile> listaErrores = new ArrayList<>();
//
//        if (listaUsuarios == null) {
//            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
//        } else if (listaUsuarios.isEmpty()) {
//            listaErrores.add(new ResultFile(0, "La lista esta vacia", "La lista esta vacia"));
//        } else {
//            int fila = 1;
//            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
//                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getNombre(), "El campo nombre es obligratorio"));
//                }
//                if (usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoMaterno(), "El campo apellido paterno es obligatorio"));
//                }
////                if (usuarioDireccion.Usuario.getFechaNacimiento() == null || usuarioDireccion.Usuario.getFechaNacimiento().equals("")){
////                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getFechaNacimiento(), "El campo de fecha de nacimiento es obligatorio"));
////                }
//                if (usuarioDireccion.Usuario.getUserName() == null || usuarioDireccion.Usuario.getUserName().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getUserName(), "El campo de username es obligatorio"));
//                }
//                if (usuarioDireccion.Usuario.getEmail() == null || usuarioDireccion.Usuario.getEmail().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getEmail(), "El campo de email es obligatorio"));
//                }
//                if (usuarioDireccion.Usuario.getPassword() == null || usuarioDireccion.Usuario.getPassword().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getPassword(), "El campo de password es obligatorio"));
//                }
//                if (usuarioDireccion.Usuario.getSexo() == null || usuarioDireccion.Usuario.getSexo().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getSexo(), "El campo sexo es obligatorio"));
//                }
//                if (usuarioDireccion.Usuario.getTelefono() == null || usuarioDireccion.Usuario.getTelefono().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getTelefono(), "El campo telefono es obligatorio"));
//                }
//                if (usuarioDireccion.Usuario.getCelular() == null || usuarioDireccion.Usuario.getCelular().equals("")) {
//                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getCelular(), "El campo celular es obligatorio"));
//                }
//                fila++;
//            }
//        }
//        return listaErrores;
//    }
//
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
//    @GetMapping("Form/{IdUsuario}")
//    public String Form(@PathVariable int IdUsuario, Model model) {
//        if (IdUsuario == 0) {
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            usuarioDireccion.Usuario = new Usuario();
//            usuarioDireccion.Usuario.Roll = new Roll();
//            usuarioDireccion.Direccion = new Direccion();
//            usuarioDireccion.Direccion.Colonia = new Colonia();
//
//            model.addAttribute("roles", RollDAOImplementation.GetAll().object);
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            //Solo se coloca Pais ya que no se deben rellenar todos los otros campos
//            Result resultPais = PaisDAOImplementation.GetAll();
//            model.addAttribute("paises", resultPais.object);
//            return "Formulario";
//        } else { //Editar
//            System.out.println("Editar");
//            Result result = usuarioDAOImplementation.DireccionById(IdUsuario);
//            model.addAttribute("usuarioDirecciones", result.object);
//            return "ViewUsuarioDireccion";
//        }
//    }
//
//    //Visualizar
//    @GetMapping("/FormularioEdit")
//    public String FormularioEditar(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
//
//        if (IdDireccion == null) { //Editar Usuario
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//
//            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.GetById(IdUsuario).object;
//            usuarioDireccion.Direccion = new Direccion();
//            usuarioDireccion.Direccion.setIdDireccion((-1));
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            model.addAttribute("roles", RollDAOImplementation.GetAll().object);
//
//        } else if (IdDireccion == 0) { //Sirve para agregar Direccion
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            usuarioDireccion.Usuario = new Usuario();
//            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
//            usuarioDireccion.Direccion = new Direccion();
//            usuarioDireccion.Direccion.setIdDireccion(0);
//
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            model.addAttribute("paises", PaisDAOImplementation.GetAll().correct ? PaisDAOImplementation.GetAll().object : null);
//
////Agregar municipio, estado, 
//        } else { //Sirve para editar dirección 
//            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            usuarioDireccion.Usuario = new Usuario();
//            usuarioDireccion.Usuario.setIdUsuario(IdUsuario);
//            usuarioDireccion.Direccion = (Direccion) DireccionDAOImplementation.GetByIdDireccion(IdDireccion).object;
//
//            //recuperar direcciones, colonias, municipios, estados, paises al momento de que el usuario quiera editar una direccion
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            model.addAttribute("paises", PaisDAOImplementation.GetAll().correct ? PaisDAOImplementation.GetAll().object : null);
//            model.addAttribute("estados", EstadoDAOImplementation.EstadoByIdPais(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais()).objects);
//            model.addAttribute("municipios", MunicipioDAOImplementation.MunicipioByIdEstado(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado()).objects);
//            model.addAttribute("colonias", ColoniaDAOImplementation.ColoniaByIdMunicipio(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio()).objects);
//
//            //Direccion
//        }
//        return "Formulario";
//    }
//

    @PostMapping("Form")
    //agregar, actualizar
    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion, BindingResult BindingResult, @RequestParam(required = false) MultipartFile imagenFile, Model model) {

        //RestTemplate ----> herramienta para interactuar con servicios web RESTful(API)
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<UsuarioDireccion> entity = new HttpEntity<>(usuarioDireccion);

        ResponseEntity<Result<UsuarioDireccion>> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/AddJPA",
                HttpMethod.POST, entity, new ParameterizedTypeReference<Result<UsuarioDireccion>>() {
        });

        
        ResponseEntity<Result<Usuario>> responseEntity = restTemplate.exchange("http://localhost:8081/usuarioapi/UpdateUsuarioJPA",
                HttpMethod.PUT, entity, new ParameterizedTypeReference<Result<Usuario>>() {
        });

        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.Usuario.setImagen(imgBase64);
            }

        } catch (Exception Ex) {

        }

        if (usuarioDireccion.Usuario.getIdUsuario() == 0) {
            System.out.println("Agregando nuevo usuario y direccion");
        } else {
            if (usuarioDireccion.Direccion.getIdDireccion() == -1) {

                System.out.println("Actualizando usuario");
            } else if (usuarioDireccion.Direccion.getIdDireccion() == 0) {

                System.out.println("Agregar Direccion");
            } else {
                System.out.println("actualizando direccion");
            }
        }
        return "redirect:/Usuario";
    }
//
//    //eliminar usuario desde la tabla de usuario
//    @GetMapping("/UsuarioDelete")
//    public String UsuarioDelete(@RequestParam int IdUsuario) {
//        usuarioDAOImplementation.UsuarioDeleteJPA(IdUsuario);
////        usuarioDAOImplementation.UsuarioDelete(IdUsuario);
//        return "redirect:/Usuario";
//    }
//
//    //eliminar direccion desde la tabla de detalles de direcciones
//    @GetMapping("/DireccionDelete")
//    public String DireccionDelete(@RequestParam int IdDireccion) {
//        DireccionDAOImplementation.DireccionDeletJPA(IdDireccion);
////        DireccionDAOImplementation.DireccionDelete(IdDireccion);
//        return "redirect:/Usuario";
//    }
//

//    @GetMapping("EstadoByIdPais/{IdPais}")
//    @ResponseBody
//    public Result EstadoByIdPais(@PathVariable int IdPais) {
//        Result result = EstadoDAOImplementation.EstadoByIdPaisJPA(IdPais);
//
//        return result;
//    }
//
//    @GetMapping("MunicipioByIdEstado/{IdEstado}")
//    @ResponseBody
//    public Result MunicipioByIdEstado(@PathVariable int IdEstado) {
//        Result result = MunicipioDAOImplementation.MunicipioByIdEstadoJPA(IdEstado);
//
//        return result;
//    }
//
//    @GetMapping("ColoniaByIdMunicipio/{IdMunicipio}")
//    @ResponseBody
//    public Result ColoniaByIdMunicipio(@PathVariable int IdMunicipio) {
//        Result result = ColoniaDAOImplementation.ColoniaByIdMunicipioJPA(IdMunicipio);
//
//        return result;
//    }
}
