package com.clientui.beans;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaiementBean{
    private int id;

    private Integer idCommande;

    private Integer montant;

    private Long numeroCarte;
}
