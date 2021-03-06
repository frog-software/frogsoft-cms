package com.frogsoft.frogsoftcms.model.history;

import com.frogsoft.frogsoftcms.model.article.Article;
import com.frogsoft.frogsoftcms.model.user.User;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@ToString
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(referencedColumnName = "id")
  private User user;

  @ManyToOne(cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(referencedColumnName = "id")
  private Article article;

  private LocalDateTime time;
}
