package com.odk.odcinterview.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String numero;
    private String genre;
    private String password;
    private String bio;
    private Date dateCreation;
    @OneToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;
    @ManyToOne(fetch = FetchType.EAGER)
    Role role;

    public Utilisateur(Long id, String image, String nom, String prenom, String email,String numero,String genre,String password) {
        this.id=id;
        this.image=image;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.numero=numero;
        this.genre=genre;
        this.password=password;
    }
}
