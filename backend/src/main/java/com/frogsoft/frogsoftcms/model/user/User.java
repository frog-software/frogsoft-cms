package com.frogsoft.frogsoftcms.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.frogsoft.frogsoftcms.model.article.Article;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@ToString
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String username;

  @JsonBackReference
  private String password;

  @ManyToMany
  private List<Article> favoriteArticles;

  @ManyToMany
  private List<Article> likeArticles;

  @ManyToMany
  private List<Article> historyArticles;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
