package com.EquipoB.AsadoYPileta.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorControlador implements ErrorController {
    
    @GetMapping("/")
    public String getError(ModelMap model, HttpServletRequest httpRequest){
        int codigo = getCodigoError(httpRequest);
        String mensaje = getMensajeError(codigo);
        model.addAttribute("codigo", codigo);
        model.addAttribute("mensaje", mensaje);        
        return "error.html";
    }
    
    @PostMapping("/")
    public String postError(ModelMap model, HttpServletRequest httpRequest){
        int codigo = getCodigoError(httpRequest);
        String mensaje = getMensajeError(codigo);
        model.addAttribute("codigo", codigo);
        model.addAttribute("mensaje", mensaje);        
        return "error.html";
    }

    private int getCodigoError(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    private String getMensajeError(int codigo) {
        String mensaje = "";
        switch (codigo) {
            case 400: {
                mensaje = "El recurso solicitado no existe.";
                break;
            }
            case 403: {
                mensaje = "No tiene permisos para acceder al recurso.";
                break;
            }
            case 401: {
                mensaje = "No se encuentra autorizado.";
                break;
            }
            case 404: {
                mensaje = "El recurso solicitado no fue encontrado.";
                break;
            }
            case 500: {
                mensaje = "Ocurrió un error interno.";
                break;
            }
        }
        return mensaje;
    }
    
    public String getErrorPath() {
        return "/error.html";
    }

}
/*




public class ErroresControlador implements ErrorController {

	@RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

		ModelAndView errorPage = new ModelAndView("error");
                
		String errorMsg = "";
                
		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
		case 400: {
			errorMsg = "El recurso solicitado no existe.";
			break;
		}
		case 403: {
			errorMsg = "No tiene permisos para acceder al recurso.";
			break;
		}
		case 401: {
			errorMsg = "No se encuentra autorizado.";
			break;
		}
		case 404: {
			errorMsg = "El recurso solicitado no fue encontrado.";
			break;
		}
		case 500: {
			errorMsg = "Ocurrió un error interno.";
			break;
		}
		}

		errorPage.addObject("codigo", httpErrorCode);
		errorPage.addObject("mensaje", errorMsg);
		return errorPage;
	}
	
	

*/
