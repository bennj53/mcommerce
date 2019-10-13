package com.clientui.web.controller;


import com.clientui.beans.ProductBean;
import com.clientui.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy produitsProxy;

    @RequestMapping("/")
    public String accueil(Model model){

        List<ProductBean> productBeans = produitsProxy.listDesProduits();
        model.addAttribute("produits", productBeans);
        return "Accueil";

    }

    @RequestMapping("/details-produit/{id}")
    public String ficheProduit(Model model, @PathVariable int id){

        ProductBean productBean = produitsProxy.recupererUnProduit(id);

        model.addAttribute("produit", productBean);

        return "FicheProduit";
    }

}
