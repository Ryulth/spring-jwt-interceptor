package com.ryulth.jwtexample.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "user")
public class User {
    protected User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Lob
    @Column(name = "user_thumb_image_url", nullable = false)
    private String thumbImageUrl = "";

    @Lob
    @Column(name = "user_image_url", nullable = false)
    private String imageUrl = "";

}

