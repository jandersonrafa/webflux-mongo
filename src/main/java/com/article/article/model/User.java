package com.article.article.model;

import javax.validation.constraints.NotBlank;

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
	private String id;

	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	private String name;

	@NotBlank
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
