package com.frogsoft.frogsoftcms.model.article;

import com.frogsoft.frogsoftcms.model.commment.Comment;
import com.frogsoft.frogsoftcms.model.history.History;
import com.frogsoft.frogsoftcms.model.user.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
  @JoinColumn(name = "id")
  private List<User> favorites;

  @ManyToMany
  @JoinColumn(name = "id")
  private List<User> likes;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "article")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<Comment> comments;

  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;
  @Column(nullable = false)
  private String cover;
  @Column(nullable = false)
  private Integer likesNum;
  @Column(nullable = false)
  private Integer favoritesNum;
  @OneToMany(cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "article")
  private Set<History> histories;

}
