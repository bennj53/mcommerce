package com.clientui.web.controller;


import com.clientui.beans.CommandeBean;
import com.clientui.beans.PaiementBean;
import com.clientui.beans.ProductBean;
import com.clientui.proxies.MicroserviceCommandesProxy;
import com.clientui.proxies.MicroservicePaimentsProxy;
import com.clientui.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy produitsProxy;

    @Autowired
    private MicroserviceCommandesProxy commandeProxy;

    @Autowired
    private MicroservicePaimentsProxy paimentsProxy;

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

    @RequestMapping("/details-produit/commander-produit/{idProduit}")
    public String commanderProduit(Model model, @PathVariable int idProduit){

        //TODO : ajouter un control produit existe

        //récupère produit commandé via microservice produit
        ProductBean productBean = produitsProxy.recupererUnProduit(idProduit);

        //creation commande
        CommandeBean commandeBean = new CommandeBean();
        commandeBean.setProductId(productBean.getId());
        commandeBean.setQuantite(1);
        commandeBean.setDateCommande(LocalDateTime.now());
        commandeBean.setCommandePayee(false);

        //transmet commande au microservice commande et récupère commande créée
        ResponseEntity<CommandeBean> responseEntity = commandeProxy.ajouterCommande(commandeBean);
        CommandeBean commande = responseEntity.getBody();

        //transmet commande et produit à vue
        model.addAttribute("commande", commande);
        model.addAttribute("produit", productBean);
        Double prixTotal = productBean.getPrix()*commandeBean.getQuantite();
        model.addAttribute("prixTotal", prixTotal);


        return "FicheCommande";
    }

    @RequestMapping("/details-commande/{id}")
    public String ficheCommande(Model model, @PathVariable int id){

        //récupère le commande via le microservice commande
        CommandeBean commandeBean = commandeProxy.recupererUneCommande(id);

        //récupère produit commandé via microservice produit
        ProductBean productBean = produitsProxy.recupererUnProduit(commandeBean.getProductId());

        //transmet commande et produit à vue
        model.addAttribute("commande", commandeBean);
        model.addAttribute("produit", productBean);
        Double prixTotal = productBean.getPrix()*commandeBean.getQuantite();
        model.addAttribute("prixTotal", prixTotal);

        return "FicheCommande";
    }

    @RequestMapping("/details-commande/payer-commande/{idCommande}")
    public String formPaiement(Model model, @PathVariable int idCommande){
        //TODO vérifier commande existe

        //récupérer commande via microservicecommande
        CommandeBean commande = commandeProxy.recupererUneCommande(idCommande);

        //récupère produit commandé via microservice produit
        ProductBean product = produitsProxy.recupererUnProduit(commande.getProductId());

        //transmet commande à vue
        model.addAttribute("commande",commande);
        model.addAttribute("produit", product);
        Double prixTotal = product.getPrix()*commande.getQuantite();
        model.addAttribute("prixTotal", prixTotal);
        PaiementBean paiementBean = new PaiementBean();
        paiementBean.setMontant(0);
        paiementBean.setIdCommande(1);
        paiementBean.setId(1);
        paiementBean.setNumeroCarte(1L);
        model.addAttribute("paiementBean", paiementBean);
        System.out.println("Before SavePaiement 9");
        return "FormPaiement";

    }

    @RequestMapping(value="/SavePaiement", method = RequestMethod.POST, params = "action=save")
    public String savePaiement(PaiementBean paiementBean){
        System.out.println("under SavePaiement");
        //System.out.println("NUMEROS DE CARTE CLIENT : " + paiementBean.getNumeroCarte());
        System.out.println("end of SavePaiement");
        //ResponseEntity<PaiementBean> responseEntity = paimentsProxy.payerUneCommande(paiementBean);
        //PaiementBean paiement = responseEntity.getBody();
        return "FicheCommande";
                //"redirect:details-commande/"+paiement.getIdCommande();
    }


//    @RequestMapping("/details-paiement/{id}")
//    public String fichePaiement(Model model, @PathVariable int id){
//
//        return "FichePaiment";
//    }

}
