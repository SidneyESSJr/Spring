package br.com.spring.mvc.mudi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.spring.mvc.mudi.model.Pedido;
import br.com.spring.mvc.mudi.model.enums.StatusPedido;
import br.com.spring.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PedidoRepository pRepository;

    @GetMapping
    public ModelAndView home() {
        List<Pedido> pedidos = pRepository.findAll();
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("pedidos", pedidos);
        return mv;
    }

    @GetMapping("/{status}")
    public ModelAndView porStatus(@PathVariable("status") String status) {
        List<Pedido> pedidos = pRepository.findByStatus(StatusPedido.valueOf(status.toUpperCase()));
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("pedidos", pedidos);
        mv.addObject("status", status);
        return mv;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String onError() {
        return "redirect:/home";
    }
}
