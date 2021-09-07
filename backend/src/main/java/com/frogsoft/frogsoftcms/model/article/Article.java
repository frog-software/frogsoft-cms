package com.frogsoft.frogsoftcms.model.article;

import com.frogsoft.frogsoftcms.model.user.User;
import java.time.LocalDateTime;
import java.util.List;
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
  @JoinColumn(referencedColumnName = "id")
  User author;
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
  private List<User> favorites;

  @ManyToMany
  private List<User> likes;

  @ManyToMany
  private List<User> histories;

  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String content;
  @Column(nullable = false)
  private String cover;
  @Column(nullable = false)
  private Integer likesNum;
  @Column(nullable = false)
  private Integer favoritesNum;

}
