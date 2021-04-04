package guru.springframework.controllers;

import guru.springframework.exceptions.BadRequestException;
import guru.springframework.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, NumberFormatException.class})
    public ModelAndView handleBadRequest(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("notFound");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("notFound");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
