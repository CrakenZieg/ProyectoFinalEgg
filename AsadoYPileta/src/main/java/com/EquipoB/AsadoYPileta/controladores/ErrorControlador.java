package com.EquipoB.AsadoYPileta.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControlador implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");
        
        int httpErrorCode = getCodigoError(httpRequest);
        String errorMsg = getMensajeError(httpErrorCode);
        String excepMsg = "Error";
        Throwable throwable = (Throwable) httpRequest.getAttribute("javax.servlet.error.exception");
        if (throwable != null && throwable.getMessage() != null) {
            excepMsg = throwable.getMessage();
        }
        
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        errorPage.addObject("excepcion", excepMsg);
        return errorPage;
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
