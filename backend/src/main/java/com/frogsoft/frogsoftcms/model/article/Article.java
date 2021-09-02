package com.frogsoft.frogsoftcms.model.article;

import com.frogsoft.frogsoftcms.model.user.User;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class Article {

  @ManyToOne
  @JoinColumn(name = "author", referencedColumnName = "id")
  User user;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Status status;
  @Column(nullable = false)
  private LocalDateTime publishDate;
  @Column(nullable = false)
  private LocalDateTime updateDate;
  @Column(nullable = false)
  private Integer views;

  @ManyToMany
  private Set<User> favorites;

  @ManyToMany
  private Set<User> likes;

  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String content;
  @Column(nullable = false)
  private String cover;

}
