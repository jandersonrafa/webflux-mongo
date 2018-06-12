package com.article.article.model;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private BigInteger id;

	@NotNull
	private String username;

	@NotNull
	private String email;

	@NotNull
	private String name;

	@NotNull
	private String password;
//
//    @ManyToMany
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
//    )
//    private List<Role> roles;
//
//    @OneToMany(mappedBy = "user")
//    public Set<Event> events;
//
//    @OneToMany(mappedBy = "user")
//    public Set<Article> articles;
}
