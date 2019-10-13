package com.clientui.beans;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductBean {

    private int id;

    private String titre;

    private String description;

    private String image;

    private Double prix;

}
